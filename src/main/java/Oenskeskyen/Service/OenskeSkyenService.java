package Oenskeskyen.Service;

import Oenskeskyen.Model.User;
import Oenskeskyen.Repository.OenskeSkyenRepository;
import org.springframework.stereotype.Service;

@Service
public class OenskeSkyenService {

    private OenskeSkyenRepository oenskeSkyenRepository;

    public OenskeSkyenService(OenskeSkyenRepository repository){
        this.oenskeSkyenRepository = repository;
    }

    /// **************************** Add and modify database functions ******************** ///

}
