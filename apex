public class Custom_Package_Controller{
    
    //variable used in page.
    Public Integer size{get;set;}
    Public Integer noOfRecords{get; set;}
    public Decimal totalCost=0;
    public boolean PieBoolean = true;
 
    //Objects
    
    public Package__c pack;
    public Product2 product{get; set;}
    //final variable 
    public static final Integer PAGE_SIZE = 2;
    //Lists
    public List<SelectOption> paginationSizeOptions{get;set;}
    public List <WrapperClass> wrapperRecordList{get;set;}
    public List<Product2> productList=new List<Product2>();
    private List<Product2> myList = new List<Product2>();   
    private List<Package_Item__c> packageItems = null;
    private Map<Id, WrapperClass> mapHoldingEditedRecords{get;set;} 
   	private Map<Id,Package_Item__c> relatedMap{get;set;}
    
    
    //MyException Class
    public class MyException extends Exception {}
    
    //wrapper class being used for Qauntity Editing for particular product.
    public class WrapperClass {
        public Integer qty {get;set;}
        public Product2 product {get;set;}
        public WrapperClass(Product2 product, Integer qty) {
            this.product = product;
            this.qty = qty;
        }
    }
    
    // Pie Data Wrapper class
    public class PieWedgeData {
        
        public String name { get; set; }
        public Integer data { get; set; }
        
        public PieWedgeData(String name, Integer data) {
            this.name = name;
            this.data = data;
        }
    }
    //constructor calling init method.
    public Custom_Package_Controller(ApexPages.StandardController controller){
        mapHoldingEditedRecords = new Map<Id, WrapperClass>();
        pack =(Package__c)controller.getRecord();
        system.debug('Package Id:'+pack.Id);
        product = new Product2();
        packageItems = new List<Package_Item__c>();
        relatedMap = new  Map<Id,Package_Item__c>();
        if(pack.Id!=null)
        { Edit(pack.Id);
        }
        else 
            init();
        
    }
    public void Edit(Id ids){
        wrapperRecordList = new List<WrapperClass>();
        List<Package_Item__c> relatedItems = new List<Package_Item__c>();
        relatedItems = Database.query('Select Id,Package__c,Product__c,Quantity__c,Shipping_Date__c from Package_Item__c where Package__c = :Ids');
        system.debug('relatedItems:'+relatedItems);
        
        for(Package_Item__c pa: relatedItems)
        {
            relatedMap.put(pa.Product__c,pa);
        }
        for (Product2 cont : (List<Product2>)setCon.getRecords()) 
        {
            if(mapHoldingEditedRecords != null && mapHoldingEditedRecords.containsKey(cont.id)){
                wrapperRecordList.add(mapHoldingEditedRecords.get(cont.id));
                
            }
            else{
                if(relatedMap.containsKey(cont.Id))
                {
                    wrapperRecordList.add(new WrapperClass(cont,Integer.valueOf(relatedMap.get(cont.Id).Quantity__c)));
                }else
                    wrapperRecordList.add(new WrapperClass(cont, 0));
            }
        }
        for(Id o: relatedMap.keySet())
        {	
            for(Product2 p : myList){
                
                if(p.id == o)
                {
                    WrapperClass wrp = new WrapperClass(p,Integer.valueOf(relatedMap.get(o).Quantity__c));
                    mapHoldingEditedRecords.put(o, wrp);
                    break;
                }
            }
            
        }
        system.debug('init 2 main mapholding wala:'+mapHoldingEditedRecords);
        
    }
    //Init method which queries the records from standard set controller.
    public void init() {
        wrapperRecordList = new List<WrapperClass>();
        for (Product2 cont : (List<Product2>)setCon.getRecords()) 
        {
            if(mapHoldingEditedRecords != null && mapHoldingEditedRecords.containsKey(cont.id)){
                wrapperRecordList.add(mapHoldingEditedRecords.get(cont.id));
                
            }
            else{
                  wrapperRecordList.add(new WrapperClass(cont, 0));
            }
        }
        system.debug('init:');
        system.debug(wrapperRecordList);
    }
    
    /** Instantiate the StandardSetController from a query locater*/
    public ApexPages.StandardSetController setCon {
        get {
            if(setCon == null) {
                String str = 'select Id,IsActive,Cost__c,Family,Name from Product2 where IsActive = true order by Cost__c';
                myList= Database.query(str);
                setCon = new ApexPages.StandardSetController(myList);
                // sets the number of records to show in each page view
                setCon.setPageSize(PAGE_SIZE);
            }
            return setCon;
        }
        set;
    }
    
    
    //Search Products According to Filters 
    public void searchProducts()
    {
        //  updateSearchItemsMap();
        
        System.debug('searchProduct==>first');
        first();
        system.debug('Product.Family = '+ product.Family);
        /**
            if product.Family is null or Any type then setCon will set with All list i.e is myList
            else productList will be set with Family equals to product.Family 
		**/
        if(product.Family == null || product.Family == 'any_type'){
            system.debug('myList size'+myList.size());
            productList.clear();
            productList.addAll(myList);
            setCon = new ApexPages.StandardSetController(productList);
        }
        else{
            productList.clear();
            for(Product2 p: myList)
            {
                if(product.Family == p.Family)
                    productList.add(p);
            }
            setCon = new ApexPages.StandardSetController(productList);
            
        }
        setCon.setPageSize(PAGE_SIZE);  
        noOfRecords = setCon.getResultSize();
        updateSearchItemsMap();
        init();
    }
   
    //This is the method which manages to remove the unedited records, and keep the records which are edited in map.
    private void updateSearchItemsMap() {
        try{
        
            for(WrapperClass wrp : wrapperRecordList){  
             system.debug('wrp.qty:'+            String.valueOf(wrp.qty));
               /*
                *if(String.isBlank(String.valueOf(wrp.qty)))
                {
                    system.debug('qty is blank');
	                // ApexPages.Message msg = new ApexPages.Message(ApexPages.Severity.Error, 'cannot be blank');
                    //wrp.product.addError('msg');
                    throw new MyException('Quantity cannot be empty.');
                }
                */
                if(wrp.qty!=0){
                    system.debug('product:'+wrp.product.name+';qty='+wrp.qty);
                    mapHoldingEditedRecords.put(wrp.product.id, wrp);
                    
                }
                else if(wrp.qty == 0 && mapHoldingEditedRecords.containsKey(wrp.product.id)) 
                {  
                    mapHoldingEditedRecords.remove(wrp.product.id);
                    
                }
                
            }
        }
        catch(MyException ex){
            
            // ApexPages.Message msg = new ApexPages.Message(ApexPages.Severity.Error, ex.getMessage());
            ApexPages.addMessages(ex);
            
        }
    }
    
    //Returns Pie Data. 
    public List<PieWedgeData> getPieData() {
        List<PieWedgeData> data = new List<PieWedgeData>(); 
        updateSearchItemsMap();
        if(pack.Id != null && PieBoolean)
        {
            PieBoolean = false;
            Map<String,Integer> r = new Map<String,Integer>();
            
            for(Id ids : relatedMap.keySet())
            {
                Package_Item__c pd = relatedMap.get(Ids);
                String fam='';
                for(Product2 o: myList)
                {
                    if(ids == o.Id)
                    {   fam=o.Family;
                     if(r.containsKey(fam))
                     {
                         r.put(fam,r.get(fam)+Integer.valueOf(pd.Quantity__c));                    
                     }else
                         r.put(fam,Integer.valueOf(pd.Quantity__c));
                    }
                }
                //data.add(new PieWedgeData(fam,Integer.valueOf(pd.Quantity__c)));
            }
            for(String s: r.keySet())
                data.add(new PieWedgeData(s,r.get(s)));
            
            
            return data;
        }
        Map<String,Integer> countFamilies = new Map<String,Integer>();
        //Map-Reduced Function
        for(Id ids: mapHoldingEditedRecords.keySet())
        {
            WrapperClass wraper = mapHoldingEditedRecords.get(ids);
            
            if(countFamilies.size()==0)
            {
                countFamilies.put(wraper.product.Family,wraper.qty);
            }
            else if(countFamilies.containsKey(wraper.product.Family))
            {
                countFamilies.put(wraper.product.Family,countFamilies.get(wraper.product.Family)+wraper.qty);                    
            }else
                countFamilies.put(wraper.product.Family,wraper.qty);
        } 
        for(String name: countFamilies.keySet())
        {
            data.add(new PieWedgeData(name,countFamilies.get(name)));
        }
        return data;
    }
    
    //save function
    public PageReference Save()
    {
        try{ 
            system.debug('map before update in save :'+mapHoldingEditedRecords);
             updateSearchItemsMap();
           // first();
            system.debug('map after update in save'+mapHoldingEditedRecords);
            totalCost =0;
            
            for(ID ids : mapHoldingEditedRecords.keySet())
            {
                totalCost += (mapHoldingEditedRecords.get(ids).qty * mapHoldingEditedRecords.get(ids).product.Cost__c);
                
            }
            
            String s= pack.Package_Type__c;
            s = s.substring(0, 2);
            system.debug('totalCost:'+totalCost);
            system.debug('string s:'+s);
            if(totalCost==0)
            {
                //totalCost = 0;
                throw new MyException('At least one item must be include in a package.');
                
            }
            if(totalCost > Decimal.valueOf(s))
            {   
                throw new MyException('TotalCost:'+totalCost+' cannot be greater than package type:'+pack.Package_Type__c);
            }
            if(pack.id!=null)
            {
               
                List<Package_Item__c> packageItemDelete =new List<Package_Item__c>();
                for(ID ids : mapHoldingEditedRecords.keySet())
                {
                    if(relatedMap.containsKey(ids))
                        packageItemDelete.add(relatedMap.get(ids));
                    
                }
                
                delete packageItemDelete;              
            }           
            
            upsert pack;
            // creating or updating related packageItems 
            for(Id ids: mapHoldingEditedRecords.keySet())
            {
                WrapperClass wraper = mapHoldingEditedRecords.get(ids) ;
                //Creating Package_Item object; 
                Package_Item__c it = new Package_Item__c();
                it.Name =pack.Name  + '-'+ wraper.product.Name;
                it.Package__c = pack.id;
                it.Product__c = wraper.product.id;
                it.Shipping_Date__c = pack.Shipping_Date__c;
                it.Quantity__c = wraper.qty;
                packageItems.add(it);
            }
            upsert packageItems;
            
            PageReference pg  = new PageReference('/lightning/r/Package__c/'+pack.id+'/view');
            return pg;
        }
        catch(Exception e)
        {
            totalCost = 0;
            ApexPages.addMessages(e);
        }
        return null;
        
    }
    //Cancel Function
    public PageReference Cancel()
    {
        PageReference pg  = new PageReference('/lightning/o/Package__c/list');
        return pg;
    }
    
    /** indicates whether there are more records after the current page set.*/
    public Boolean hasNext {
        get {
            
            return setCon.getHasNext();
        }
        set;
    }
    
    /** indicates whether there are more records before the current page set.*/
    public Boolean hasPrevious {
        get {
            return setCon.getHasPrevious();
        }
        set;
    }
    
    /** returns the page number of the current page set*/
    public Integer pageNumber {
        get {
            return setCon.getPageNumber();
        }
        set;
    }
    
    /** return total number of pages for page set*/
    Public Integer getTotalPages(){
        Decimal totalSize = setCon.getResultSize();
        Decimal pageSize = setCon.getPageSize();
        Decimal pages = totalSize/pageSize;
        return (Integer)pages.round(System.RoundingMode.CEILING);
    }
    
    /** returns the first page of the page set*/
    public void first() {
        system.debug('first');
        updateSearchItemsMap();
        setCon.first();
        init();
    }
    
    /** returns the last page of the page set*/
    public void last() {
        updateSearchItemsMap();
        setCon.last();
        init();
    }
    
    /** returns the previous page of the page set*/
    public void previous() {
        updateSearchItemsMap();
        setCon.previous();
        init();
    }
    
    /** returns the next page of the page set*/
    public void next() {
        updateSearchItemsMap();
        setCon.next();
        init();
    }
    
}
