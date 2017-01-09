package store;

public class StoreList {
	static final String CARDIAC = "CARDIAC MAIN STORE"; 
	static final String CENTRAL = "CENTRAL STORE";
	static final String CATHLAB = "CATH LAB STORE";
	static final String VASCULAR = "VASCULAR CATH LAB STORE";
	static final String IMCU = "IMCU + SICU STORE";
	static final String GRN = "Item receive against GRN";
	
	public static boolean isStoreInList(String store){
		if(store.equalsIgnoreCase(CARDIAC) || store.equalsIgnoreCase(CENTRAL) || store.equalsIgnoreCase(CATHLAB) || store.equalsIgnoreCase(VASCULAR) || store.equalsIgnoreCase(IMCU)){
			return true;
		}
		return false;
	}
}
