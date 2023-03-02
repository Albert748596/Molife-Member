package com.mem.dao;

import com.mem.model.MemVO;
import java.util.List;

public interface MemDAO_interface {
  void insert(MemVO paramMemVO);
  
  void update(MemVO paramMemVO);
  
  void delete(Integer paramInteger);
  
  MemVO findByPrimaryKey(Integer paramInteger);
  
  List<MemVO> getAll();
  
  MemVO login(String memEmail, String memPsd);
  
  
}