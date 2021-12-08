package com.shooterj.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 基础实体类
 *
 */
@ApiModel(description = "基础实体类")
public abstract class BaseModel<PK extends Serializable> implements Serializable {
    private static final long serialVersionUID = 3796984803158565007L;

    @ApiModelProperty(name = "createBy", notes = "创建人ID")
    protected String createBy;

    @ApiModelProperty(name = "updateBy", notes = "更新人ID")
    protected String updateBy;

    @ApiModelProperty(name = "createTime", notes = "创建时间")
    protected LocalDateTime createTime;

    @ApiModelProperty(name = "updateTime", notes = "更新时间")
    protected LocalDateTime updateTime;

    @ApiModelProperty(name = "createOrgId", notes = "创建组织ID")
    protected String createOrgId;

    @ApiModelProperty(name = "createOrgName", notes = "创建组织Name")
    protected String createOrgName;

    @ApiModelProperty(name = "createName", notes = "创建人Name")
    protected String createName;

    @ApiModelProperty(name = "updateName", notes = "更新人Name")
    protected String updateName;

    @ApiModelProperty(name = "company", notes = "多租户公司")
    protected String company;

    @ApiModelProperty(name = "orgVersion", notes = "组织架构版本号")
    protected String orgVersion;

    @ApiModelProperty(name = "roleVersion", notes = "角色版本号")
    protected String roleVersion;


    public String getCreateOrgName() {
        return createOrgName;
    }

    public void setCreateOrgName(String createOrgName) {
        this.createOrgName = createOrgName;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public final String getCreateBy() {
        return createBy;
    }

    public final void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public final String getUpdateBy() {
        return updateBy;
    }

    public final void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public final LocalDateTime getCreateTime() {
        return createTime;
    }

    public final void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public final LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public final void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public final String getCreateOrgId() {
        return createOrgId;
    }

    public final void setCreateOrgId(String createOrgId) {
        this.createOrgId = createOrgId;
    }

    public String getOrgVersion() {
        return orgVersion;
    }

    public void setOrgVersion(String orgVersion) {
        this.orgVersion = orgVersion;
    }

    public String getRoleVersion() {
        return roleVersion;
    }

    public void setRoleVersion(String roleVersion) {
        this.roleVersion = roleVersion;
    }

    /**
     * 设置主键ID
     *
     * @param id
     */
    public abstract void setId(PK id);

    /**
     * 获取主键ID
     *
     * @return
     */
    public abstract PK getId();
}
