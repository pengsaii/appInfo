package cn.appsys.pojo;

/**
 * 查询App基础信息VO
 * @author Charles
 *
 */
public class QueryAppInfoVO {

	private String querySoftwareName; // 软件名称
	private Integer queryStatus; // 状态
	private Integer queryFlatformId; // 平台
	private Integer queryCategoryLevel1; // 1级分类
	private Integer queryCategoryLevel2; // 2级分类
	private Integer queryCategoryLevel3; // 3级分类
	private Integer pageIndex; // 当前页
	private Integer startIndex; // 起始页
	private Integer pageSize; // 每页显示条数
	
	public Integer getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}
	public String getQuerySoftwareName() {
		return querySoftwareName;
	}
	public void setQuerySoftwareName(String querySoftwareName) {
		this.querySoftwareName = querySoftwareName;
	}
	public Integer getQueryStatus() {
		return queryStatus;
	}
	public void setQueryStatus(Integer queryStatus) {
		this.queryStatus = queryStatus;
	}
	public Integer getQueryFlatformId() {
		return queryFlatformId;
	}
	public void setQueryFlatformId(Integer queryFlatformId) {
		this.queryFlatformId = queryFlatformId;
	}
	public Integer getQueryCategoryLevel1() {
		return queryCategoryLevel1;
	}
	public void setQueryCategoryLevel1(Integer queryCategoryLevel1) {
		this.queryCategoryLevel1 = queryCategoryLevel1;
	}
	public Integer getQueryCategoryLevel2() {
		return queryCategoryLevel2;
	}
	public void setQueryCategoryLevel2(Integer queryCategoryLevel2) {
		this.queryCategoryLevel2 = queryCategoryLevel2;
	}
	public Integer getQueryCategoryLevel3() {
		return queryCategoryLevel3;
	}
	public void setQueryCategoryLevel3(Integer queryCategoryLevel3) {
		this.queryCategoryLevel3 = queryCategoryLevel3;
	}
	public Integer getStartIndex() {
		return startIndex;
	}
	public void setStartIndex(Integer startIndex) {
		this.startIndex = startIndex;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
	
}
