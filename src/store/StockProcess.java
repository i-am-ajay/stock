package store;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class StockProcess {
	
	Connection con;
	Set<String> storeSet = new HashSet<>();
	//Map<String,Float[]> storeMap = new HashMap<>();
	List<List<String>> resultList = new ArrayList<>();
	
	public List<List<String>> readData() throws SQLException, ClassNotFoundException{
		Connection con = DBConnection.CONNECTION.getConnection();
		/* Item Code
		 * Store Code
		 * From Transaction Date
		 * To Transaction Date
		 * GRN No
		 * */
		CallableStatement callBack = con.prepareCall("exec sgrhsp_HMS_InvTransaction_ItemTransactions_With_GRN_Details  ?,?,?,?,?");
		callBack.setString(1,"MEDCONCATMIS4"); //Item Code
		callBack.setString(2, ""); // Store Code
		callBack.setString(3, "2016-06-01");// From Transaction Date
		callBack.setString(4, "2016-12-31"); // From Transaction Date
		callBack.setString(5,""); // set grn number
		
		ResultSet resultSet = callBack.executeQuery();
		float [] valQtyArray = new float[2];
		String storeName = null;
		//loop till last record
		while(resultSet.next()){
			// For each record from recordset : get relevant columns from record and store in a list.
			storeName = resultSet.getString("STORENAME"); 
			List<String> list = new ArrayList<>();
			list.add(storeName);
			list.add(resultSet.getString("Qty"));
			list.add(resultSet.getString("TValue"));
			list.add(resultSet.getString("DESCRIPTION")); // remarks of transaction
			
			// add store to set, to have a set of all stores.
			storeSet.add(storeName);
			// add list of elements to record list.
			resultList.add(list);
		}
		System.out.println("Process completed....");
		System.out.println(resultList.size());
		callBack.close();
		con.close();
		return resultList;
	}
	// process list contains lists of elements.
	public void processList(List<List<String>> list){
		System.out.println("Running Process List");
			String item = null;
			String desc = null;
			float val = 0.0f;
			float qty = 0.0f;
			for(String store : storeSet){
				if(StoreList.isStoreInList(store)){
					RecieveStock recieveStockForStore = new RecieveStock(store);
					TransferredStock transferredStockForStore = new TransferredStock(store);
					for(List<String> dataList : list){
						item = dataList.get(0);
						if(item.equals(store)){
							qty = Float.valueOf(dataList.get(1));
							val = Float.valueOf(dataList.get(2));
							desc = dataList.get(3);
							if(qty > 0){
								transferredStockForStore.decisionRecieveStock(desc, val,qty);
							}
							else if(qty < 0){
								recieveStockForStore.decisionRecieveStock(desc, val,qty);
							}
							else{
								System.out.println("Wrong Entry");
							}
						}
					}
					Map<String,Double> recieveStockMap = recieveStockForStore.getRecieveStockValueMap();
					Map<String,Double> transferStockMap = transferredStockForStore.getTransferredStockMap();
					Map<String,Double> recieveStockQtyMap = recieveStockForStore.getRecieveStockQtyMap();
					Map<String,Double> transferStockQtyMap =  transferredStockForStore.getTransferredQtyStockMap();
					System.out.println("--------------------------------------------");
					System.out.println(store);
					for(String key : recieveStockMap.keySet()){
						//System.out.printf("Qty : %f of Value : %f transferred to %s %n",recieveStockQtyMap.get(key),recieveStockMap.get(key),key);
						System.out.printf("%s %f %f recieved%n",key, recieveStockQtyMap.getOrDefault(key,0.0),recieveStockMap.getOrDefault(key,0.0));
					}
					
					for(String key : transferStockMap.keySet()){
						System.out.printf("%s %f %f transferred%n",key,transferStockQtyMap.getOrDefault(key,0.0),transferStockMap.getOrDefault(key,0.0));
						//System.out.printf("Qty : %f of Value : %f recieved from %s %n",transferStockQtyMap.get(key),transferStockMap.get(key),key);
					}
					System.out.println("--------------------------------------------");
				}
			}
				
	}
	
	public static void main(String [] args){
		StockProcess process = new StockProcess();
		try{
			List<List<String>> dataList = process.readData();
			process.processList(dataList);
		}
		catch(SQLException | ClassNotFoundException ex){
			ex.printStackTrace();
		}
	}
}
