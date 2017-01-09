package store;

import java.util.HashMap;
import java.util.Map;

public class RecieveStock {
	private String storeName;
	Map<String,Double> recieveStockValueMap = new HashMap<>();
	Map<String,Double> recieveStockQtyMap = new HashMap<>();
	
	public RecieveStock(String storeName){
		setStore(storeName);
	}
	public void setStore(String storeName){
		this.storeName = storeName;
	}
	public String getStore(){
		return storeName;
	}
	
	public void setRecieveStockValueMap(Map<String,Double> stockMap){
		recieveStockValueMap = new HashMap<>();
	}
	public Map<String,Double> getRecieveStockValueMap(){
		return recieveStockValueMap;
	}
	
	public void setRecieveStockQtyMap(){
		recieveStockQtyMap = new HashMap<>();
	}
	public Map<String,Double> getRecieveStockQtyMap(){
		return recieveStockQtyMap;
	}
	
	public void addStockToMap(String store, Double stock){
		if(recieveStockValueMap.get(store) == null){
			recieveStockValueMap.put(store, stock);
		}
		else{
			recieveStockValueMap.put(store,(recieveStockValueMap.get(store)+stock));
		}
	}
	public void addQtyToMap(String store, Double qty){
		if(recieveStockQtyMap.get(store) == null){
			recieveStockQtyMap.put(store, qty);
		}
		else{
			recieveStockQtyMap.put(store,(recieveStockQtyMap.get(store)+qty));
		}
	}
	
	public void decisionRecieveStock(String remark, double value, double qty){
		if(remark.contains("store")){
			if(remark.contains(StoreList.CARDIAC) && !StoreList.CARDIAC.equals(storeName)){
				addStockToMap(StoreList.CARDIAC,value);
				addQtyToMap(StoreList.CARDIAC,qty);
			}
			else if(remark.contains(StoreList.VASCULAR) && !StoreList.VASCULAR.equals(storeName)){
				addStockToMap(StoreList.VASCULAR,value);
				addQtyToMap(StoreList.VASCULAR,qty);
			}
			if(remark.contains(StoreList.CENTRAL) && !StoreList.CENTRAL.equals(storeName)){
				addStockToMap(StoreList.CENTRAL,value);
				addQtyToMap(StoreList.CENTRAL,qty);
			}
			if(remark.contains(StoreList.CATHLAB) && !(remark.contains(StoreList.VASCULAR)) && !StoreList.CATHLAB.equals(storeName)){
				addStockToMap(StoreList.CATHLAB,value);
				addQtyToMap(StoreList.CATHLAB,qty);
			}
			if(remark.contains(StoreList.IMCU) && !StoreList.IMCU.equals(storeName)){
				addStockToMap(StoreList.IMCU,value);
				addQtyToMap(StoreList.IMCU,qty);
			}
		}
		else if(remark.contains("Item receive against GRN")){
			addStockToMap("GRN",value);
			addQtyToMap("GRN",qty);
		}
		else if(remark.contains("Quantity is deducted")){
			addStockToMap("Patient Consumption",value);
			addQtyToMap("Patient Consumption",qty);
		}
		else if(remark.contains("Quantity is added from adjustment/patitent")){
			addStockToMap("Patient Returns",value);
			addQtyToMap("Patient Consumption",qty);
		}
		else{
			addStockToMap("Misc",value);
			addQtyToMap("Misc",qty);
		}
	}
	
	@Override
	public boolean equals(Object obj){
		String store = (String) obj;
		return this.getStore().equals(store);
	}
	
	@Override
	public int hashCode(){
		return this.getStore().hashCode()+100;
	}
}
