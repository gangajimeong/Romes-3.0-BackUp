package com.company.ROMES.interfaces.service.StandardInfo;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import com.company.ROMES.entity.CommonCode;
import com.company.ROMES.entity.LocationMaster;


public interface LocationMasterService {
 public List<LocationMaster>SelectAllbyfalse();
 public LocationMaster SelectById(int id);
 public JSONArray SelectSubLocation(int id);
 public JSONObject SelectTopLocation(int id);
 public String getImageUrl(int id);
 public List<LocationMaster>SelectTop();
 public boolean createLocation(LocationMaster locationMaster,String filename);
 public boolean updateLocation(LocationMaster locationMaster, String filename);
 public boolean deleteLocation(int id);
 public boolean SaveLocationImg(MultipartFile file);
 public JSONArray LocationNavi(int id);
 public List<CommonCode>SelectAboutLocation();
 public JSONObject getLocationMainEventData(int id);
 public JSONObject printBarcode(int id, String printName);
 public JSONArray printList();
}
