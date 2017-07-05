package pk.webdoor.fx.service;

import java.util.List;
import javax.inject.Inject;

import pk.webdoor.fx.dao.IWDPackageDao;
import pk.webdoor.model.WDPackage;

public class PackageService {

    //private IWDPackageDao packageDao = new WDPackageDaoImpl();
    @Inject
    private IWDPackageDao packageDao;

    public List<WDPackage> getPackages() {
        return packageDao.getPackages();
    }

    public WDPackage getPackageByDetails(Integer details) {
        return packageDao.getPackageByDetails(details);
    }

    public WDPackage getPackageByName(String name) {
        return packageDao.getPackageByName(name);
    }

}
