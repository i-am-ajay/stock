package store;

import java.util.HashMap;
import java.util.Map;

public class TransferredStock {
	private String storeName;
	Map<String,Double> transferredStockMap = new HashMap<>();
	Map<String,Double> transferredQtyStockMap = new HashMap<>();
	
	public TransferredStock(String storeName){
		setStore(storeName);
	}
	public void setStore(String storeName){
		this.storeName = storeName;
	}
	public String getStore(){
		return storeName;
	}
	
	public void setTransferredQtyStockMap(Map<String,Double> stockMap){
		transferredQtyStockMap = new HashMap<>();
	}
	public Map<String,Double> getTransferredQtyStockMap(){
		return transferredQtyStockMap;
	}
	
	public void setTransferredStockMap(Map<String,Double> stockMap){
		transferredStockMap = new HashMap<>();
	}
	public Map<String,Double> getTransferredStockMap(){
		return transferredStockMap;
	}
	
	public void addStockToMap(String store, Double value){
		if(transferredStockMap.get(store) == null){
			transferredStockMap.put(store, value);
		}
		else{
			transferredStockMap.put(store,(transferredStockMap.get(store)+value));
		}
	}
	
	public void addStockToQtyMap(String store, Double value){
		if(transferredQtyStockMap.get(store) == null){
			transferredQtyStockMap.put(store, value);
		}
		else{
			transferredQtyStockMap.put(store,(transferredQtyStockMap.get(store)+value));
		}
	}
	
	public void decisionRecieveStock(String remark, double value,double qty){
		if(remark.contains("store")){
			if(remark.contains(StoreList.CARDIAC) && !StoreList.CARDIAC.equals(storeName)){
				addStockToMap(StoreList.CARDIAC,value);
				addStockToQtyMap(StoreList.CARDIAC, qty);
			}
			else if(remark.contains(StoreList.VASCULAR) && !StoreList.VASCULAR.equals(storeName)){
				addStockToMap(StoreList.VASCULAR,value);
				addStockToQtyMap(StoreList.VASCULAR, qty);
			}
			else if(remark.contains(StoreList.CENTRAL)  && !StoreList.CENTRAL.equals(storeName)){
				addStockToMap(StoreList.CENTRAL,value);
				addStockToQtyMap(StoreList.CENTRAL, qty);
			}
			else if(remark.contains(StoreList.CATHLAB) && !(remark.contains(StoreList.VASCULAR)) && !StoreList.CATHLAB.equals(storeName)){
				addStockToMap(StoreList.CATHLAB,value);
				addStockToQtyMap(StoreList.CATHLAB, qty);
			}
			else if(remark.contains(StoreList.IMCU) && !StoreList.IMCU.equals(storeName)){
				addStockToMap(StoreList.IMCU,value);
				addStockToQtyMap(StoreList.IMCU, qty);
			}
			
		}
		else if(remark.contains("Item receive against GRN")){
			addStockToMap("GRN",value);
			addStockToQtyMap("GRN", qty);
		}
		else if(remark.contains("Quantity is deducted")){
			addStockToMap("Patient Consumption",value);
			addStockToQtyMap("Patient Consumption", qty);
		}
		else if(remark.contains("Quantity is added from adjustment/patitent")){
			addStockToMap("Patient Returns",value);
			addStockToQtyMap("Patient Returns", qty);
		}
		else{
			addStockToMap("Misc",value);
			addStockToQtyMap("Misc", qty);
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
