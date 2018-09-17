package cn.appsys.service.dataDictionary;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appsys.dao.datadictionary.DataDictionaryMapper;
import cn.appsys.pojo.DataDictionary;

@Service
public class DataDIctionaryServiceImpl implements DataDictionaryService {
	@Resource
	private DataDictionaryMapper dataDictionaryMapper;

	@Override
	public List<DataDictionary> getDataDictionaryListByTypeCode(String typeCode) {
		return dataDictionaryMapper.getDataDictionaryListByTypeCode(typeCode);
	}
}
