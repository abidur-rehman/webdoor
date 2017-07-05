package pk.webdoor.fx.dao;

import java.util.List;

import pk.webdoor.model.WDPackage;


public interface IWDPackageDao {

	List<WDPackage> getPackages();
	
	WDPackage getPackageByDetails(Integer details);
        
        WDPackage getPackageByName(String name);

}
