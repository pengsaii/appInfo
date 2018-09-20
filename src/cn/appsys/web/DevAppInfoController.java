package cn.appsys.web;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.mysql.jdbc.StringUtils;

import cn.appsys.pojo.AppCategory;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.AppVersion;
import cn.appsys.pojo.DataDictionary;
import cn.appsys.pojo.DevUser;
import cn.appsys.pojo.QueryAppInfoVO;
import cn.appsys.service.appcategory.AppCategoryService;
import cn.appsys.service.appinfo.AppInfoService;
import cn.appsys.service.appversion.AppVersionService;
import cn.appsys.service.dataDictionary.DataDictionaryService;
import cn.appsys.util.PageBean;

@Controller
@RequestMapping("/dev/app")
public class DevAppInfoController {
	@Resource
	private AppInfoService appInfoService;
	@Resource
	private DataDictionaryService dataDictionaryService;
	@Resource
	private AppCategoryService appCategoryService;
	@Resource
	private AppVersionService appVersionService;
	

	@RequestMapping("appview/{id}")
	public String examineAppInfo(Model model,@PathVariable int id) {
		AppInfo appInfo = appInfoService.getAppInfoById(id);
		List<AppVersion> appVersionList = appVersionService.getAppVersionById(id);
		model.addAttribute("appInfo",appInfo);
		model.addAttribute("appVersionList",appVersionList);
		return "developer/appinfoview";
	}
	

	/**
	 * 保存修改后的appInfo
	 * @param appInfo
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/appinfomodifysave",method=RequestMethod.POST)
	public String modifySave(AppInfo appInfo,HttpSession session,HttpServletRequest request,
							@RequestParam(value="attach",required= false) MultipartFile attach){		
		String logoPicPath =  null;
		String logoLocPath =  null;
		String APKName = appInfo.getAPKName();
		if(!attach.isEmpty()){
			String path = request.getSession().getServletContext().getRealPath("statics"+File.separator+"uploadfiles");
			String oldFileName = attach.getOriginalFilename();//原文件名
			String prefix = FilenameUtils.getExtension(oldFileName);//原文件后缀
			int filesize = 500000;
			if(attach.getSize() > filesize){//上传大小不得超过 50k
            	 return "redirect:/dev/flatform/app/appinfomodify?id="+appInfo.getId()
						 +"&error=error4";
            }else if(prefix.equalsIgnoreCase("jpg") || prefix.equalsIgnoreCase("png") 
			   ||prefix.equalsIgnoreCase("jepg") || prefix.equalsIgnoreCase("pneg")){//上传图片格式
				 String fileName = APKName + ".jpg";//上传LOGO图片命名:apk名称.apk
				 File targetFile = new File(path,fileName);
				 if(!targetFile.exists()){
					 targetFile.mkdirs();
				 }
				 try {
					attach.transferTo(targetFile);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return "redirect:/dev/flatform/app/appinfomodify?id="+appInfo.getId()
							+"&error=error2";
				} 
				 logoPicPath = request.getContextPath()+"/statics/uploadfiles/"+fileName;
				 logoLocPath = path+File.separator+fileName;
            }else{
            	return "redirect:/dev/flatform/app/appinfomodify?id="+appInfo.getId()
						 +"&error=error3";
            }
		}
		appInfo.setModifyBy(((DevUser)session.getAttribute("devLoginUser")).getId());
		appInfo.setModifyDate(new Date());
		appInfo.setLogoLocPath(logoLocPath);
		appInfo.setLogoPicPath(logoPicPath);
		try {
			if(appInfoService.modify(appInfo)){
				return "redirect:/dev/app/list";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "developer/appinfomodify";
	}
	
	/**
	 * 修改文件时选择删除图片
	 * @param flag
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delfile",method=RequestMethod.GET)
	@ResponseBody
	public Object delFile(@RequestParam(value="flag",required=false) String flag,
						 @RequestParam(value="id",required=false) String id){
		HashMap<String, String> resultMap = new HashMap<String, String>();
		String fileLocPath = null;
		if(flag == null || flag.equals("") ||
			id == null || id.equals("")){
			resultMap.put("result", "failed");
		}else if(flag.equals("logo")){//删除logo图片（操作app_info）
			try {
				fileLocPath = (appInfoService.getAppInfo(Integer.parseInt(id), null)).getLogoLocPath();
				File file = new File(fileLocPath);
			    if(file.exists())
			     if(file.delete()){//删除服务器存储的物理文件
						if(appInfoService.deleteAppLogo(Integer.parseInt(id))){//更新表
							resultMap.put("result", "success");
						 }
			    }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}/*else if(flag.equals("apk")){//删除apk文件（操作app_version）
			try {
				fileLocPath = (appVersionService.getAppVersionById(Integer.parseInt(id))).getApkLocPath();
				File file = new File(fileLocPath);
			    if(file.exists())
			     if(file.delete()){//删除服务器存储的物理文件
						if(appVersionService.deleteApkFile(Integer.parseInt(id))){//更新表
							resultMap.put("result", "success");
						 }
			    }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/
		return JSON.toJSONString(resultMap);
	}
	

	/**
	 * 修改appInfo信息（跳转到修改appInfo页面）
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/appinfomodify",method=RequestMethod.GET)
	public String modifyAppInfo(@RequestParam("id") String id,
								@RequestParam(value="error",required= false)String fileUploadError,
								Model model){
		AppInfo appInfo = null;
		if(null != fileUploadError && fileUploadError.equals("error1")){
			fileUploadError = "APK信息不完整！";
		}else if(null != fileUploadError && fileUploadError.equals("error2")){
			fileUploadError	= "上传失败";
		}else if(null != fileUploadError && fileUploadError.equals("error3")){
			fileUploadError = "上传文件格式不正确";
		}else if(null != fileUploadError && fileUploadError.equals("error4")){
			fileUploadError = "上传文件过大";
		}
		try {
			appInfo = appInfoService.getAppInfo(Integer.parseInt(id),null);
		}catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute(appInfo);
		model.addAttribute("fileUploadError",fileUploadError);
		return "developer/appinfomodify";
	}
	
	@ResponseBody
	@RequestMapping("datadictionarylist/{tcode}")
	public String getDateDictionary(@PathVariable String tcode){
		List<DataDictionary> dataDictionary = dataDictionaryService.getDataDictionaryListByTypeCode(tcode);
		return JSON.toJSONString(dataDictionary);
	}
	@ResponseBody
	@RequestMapping("datadictionarylist2")
	public String getDateDictionary2(@RequestParam String tcode){
		List<DataDictionary> dataDictionary = dataDictionaryService.getDataDictionaryListByTypeCode(tcode);
		return JSON.toJSONString(dataDictionary);
	}

	
	/**
	 * 三级联动 分类查询
	 * @param pid
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getclist2")
	public String getCategoryList2(@RequestParam Integer pid) {
		List<AppCategory> appCategoryList = appCategoryService.getAppCategoryListByParentId(pid);
		return JSON.toJSONString(appCategoryList);
	}
	@ResponseBody
	@RequestMapping("getclist/{pid}")
	public String getCategoryList(@PathVariable Integer pid) {
		List<AppCategory> appCategoryList = appCategoryService.getAppCategoryListByParentId(pid);
		return JSON.toJSONString(appCategoryList);
	}

	@RequestMapping(value = "/list")
	public String AppList(Model model, @ModelAttribute QueryAppInfoVO queryAppInfoVO) {
		// 当前页
		Integer currentPageNo = 1;
		if (queryAppInfoVO.getPageIndex() != null) {
			currentPageNo = queryAppInfoVO.getPageIndex();
		}
		// 每页显示数量
		Integer pageSize = 5;
		PageBean<AppInfo> pageBean = new PageBean<AppInfo>();
		pageBean.setCurrentPageNo(currentPageNo);
		pageBean.setPageSize(pageSize);

		appInfoService.getAppInfoList(pageBean, queryAppInfoVO);

		// 查询状态列表 statusList
		List<DataDictionary> statusList = dataDictionaryService.getDataDictionaryListByTypeCode("APP_STATUS");
		// 查询平台列表 flatFormList
		List<DataDictionary> flatFormList = dataDictionaryService.getDataDictionaryListByTypeCode("APP_FLATFORM");
		// 查询所有的1级分类
		List<AppCategory> categoryLevel1List = appCategoryService.getAppCategoryListByParentId(null);
		// 完善分类的回显
		// 如果传了一级分类 说明你选择过 所以肯定触发过三级联动 认为应该将二级分类全部查询
		if (queryAppInfoVO.getQueryCategoryLevel1() != null) {
			List<AppCategory> categoryLevel2List = appCategoryService
					.getAppCategoryListByParentId(queryAppInfoVO.getQueryCategoryLevel1());
			model.addAttribute("categoryLevel2List", categoryLevel2List);
		}
		if (queryAppInfoVO.getQueryCategoryLevel2() != null) {
			List<AppCategory> categoryLevel3List = appCategoryService
					.getAppCategoryListByParentId(queryAppInfoVO.getQueryCategoryLevel2());
			model.addAttribute("categoryLevel3List", categoryLevel3List);
		}
		model.addAttribute("statusList", statusList);
		model.addAttribute("flatFormList", flatFormList);
		model.addAttribute("categoryLevel1List", categoryLevel1List);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("queryAppInfoVO",queryAppInfoVO);
		return "developer/appinfolist";
	}
	
	/**
	 * 跳转至添加基础信息页面
	 */
	@RequestMapping(value="/appinfoadd")
	public String add() {
		return "developer/appinfoadd";
	}
	
	/**
	 * 判断APKName是否唯一
	 */
	@ResponseBody
	@RequestMapping(value="/apkexist")
	public String apkNameIsExit(@RequestParam String APKName) {
		HashMap<String,String> resultMap = new HashMap<String,String>();
		if(StringUtils.isNullOrEmpty(APKName)) {
			resultMap.put("APKName", "empty");
		}else {
			AppInfo appInfo = null;
			appInfo = appInfoService.getAppInfo(null,APKName);
			if(null != appInfo) {
				resultMap.put("APKName","exist");
			}else {
				resultMap.put(APKName, "noexist");
			}
		}
		return JSON.toJSONString(resultMap);
	}
	
	/**
	 * 新增app基础信息
	 */
	@RequestMapping(value="/appinfoaddsave",method=RequestMethod.POST)
	public String addSave(AppInfo appInfo,HttpSession session,HttpServletRequest request,
					@RequestParam(value="a_logoPicPath",required= false) MultipartFile attach){		
		
		String logoPicPath =  null;
		String logoLocPath =  null;
		if(!attach.isEmpty()){
			String path = request.getSession().getServletContext().getRealPath("statics"+java.io.File.separator+"uploadfiles");
			String oldFileName = attach.getOriginalFilename();//原文件名
			String prefix = FilenameUtils.getExtension(oldFileName);//原文件后缀
			int filesize = 500000;
			if(attach.getSize() > filesize){//上传大小不得超过 50k
				request.setAttribute("fileUploadError", "*上传文件过大！");
				return "developer/appinfoadd";
            }else if(prefix.equalsIgnoreCase("jpg") || prefix.equalsIgnoreCase("png") 
			   ||prefix.equalsIgnoreCase("jepg") || prefix.equalsIgnoreCase("pneg")){//上传图片格式
				 String fileName = appInfo.getAPKName() + ".jpg";//上传LOGO图片命名:apk名称.apk
				 File targetFile = new File(path,fileName);
				 if(!targetFile.exists()){
					 targetFile.mkdirs();
				 }
				 try {
					attach.transferTo(targetFile);
				} catch (Exception e) {
					e.printStackTrace();
					request.setAttribute("fileUploadError","上传失败");
					return "developer/appinfoadd";
				} 
				 logoPicPath = request.getContextPath()+"/statics/uploadfiles/"+fileName;
				 logoLocPath = path+File.separator+fileName;
			}else{
				request.setAttribute("fileUploadError", "上传文件格式不正确");
				return "developer/appinfoadd";
			}
		}
		appInfo.setCreatedBy(((DevUser)session.getAttribute("devLoginUser")).getId());
		appInfo.setCreationDate(new Date());
		appInfo.setLogoPicPath(logoPicPath);
		appInfo.setLogoLocPath(logoLocPath);
		appInfo.setDevId(((DevUser)session.getAttribute("devLoginUser")).getId());
		appInfo.setStatus(1);
		try {
			if(appInfoService.add(appInfo)){
				return "redirect:/dev/app/list";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "developer/appinfoadd";
	}
}
