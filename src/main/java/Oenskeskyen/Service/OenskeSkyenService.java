package Oenskeskyen.Service;

import Oenskeskyen.Repository.OenskeSkyenRepository;
import org.springframework.stereotype.Service;

@Service
public class OenskeSkyenService {

    private OenskeSkyenRepository oenskeSkyenRepository;

    public OenskeSkyenService(OenskeSkyenRepository repository){
        this.oenskeSkyenRepository = repository;
    }
}
