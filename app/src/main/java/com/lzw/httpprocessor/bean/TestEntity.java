package com.lzw.httpprocessor.bean;

import java.util.List;

public class TestEntity {


    /**
     * id : null
     * isNewRecord : true
     * remarks : null
     * createDate : null
     * updateDate : null
     * office : null
     * companyId : null
     * attachment : null
     * attachments : null
     * value : 1
     * label : 派出机构
     * type : null
     * description : null
     * sort : null
     * parentId : null
     * officeList : [{"id":"7a66b30236334da8b58e0b1894780d8b","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"office":null,"companyId":null,"attachment":null,"attachments":null,"parentIds":null,"name":"宝马区","sort":30,"area":null,"code":null,"type":"2","grade":null,"address":null,"zipCode":null,"master":null,"phone":null,"fax":null,"email":null,"useable":null,"bmsx":null,"bmsxmc":null,"primaryPerson":null,"deputyPerson":null,"childDeptList":null,"tzqsZt":null,"parentId":"0"},{"id":"b895bab1af7842dba3e4e70ca315d047","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"office":null,"companyId":null,"attachment":null,"attachments":null,"parentIds":null,"name":"池北区管委会","sort":30,"area":null,"code":null,"type":"2","grade":null,"address":null,"zipCode":null,"master":null,"phone":null,"fax":null,"email":null,"useable":null,"bmsx":null,"bmsxmc":null,"primaryPerson":null,"deputyPerson":null,"childDeptList":null,"tzqsZt":null,"parentId":"0"},{"id":"cdc28916db33467bbf3e37410220d3c3","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"office":null,"companyId":null,"attachment":null,"attachments":null,"parentIds":null,"name":"池西区管委会","sort":30,"area":null,"code":null,"type":"2","grade":null,"address":null,"zipCode":null,"master":null,"phone":null,"fax":null,"email":null,"useable":null,"bmsx":null,"bmsxmc":null,"primaryPerson":null,"deputyPerson":null,"childDeptList":null,"tzqsZt":null,"parentId":"0"},{"id":"db916fd28cb04b7db64e8ce5c922c700","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"office":null,"companyId":null,"attachment":null,"attachments":null,"parentIds":null,"name":"池南区管委会","sort":30,"area":null,"code":null,"type":"2","grade":null,"address":null,"zipCode":null,"master":null,"phone":null,"fax":null,"email":null,"useable":null,"bmsx":null,"bmsxmc":null,"primaryPerson":null,"deputyPerson":null,"childDeptList":null,"tzqsZt":null,"parentId":"0"}]
     */

    private Object id;
    private boolean isNewRecord;
    private Object remarks;
    private Object createDate;
    private Object updateDate;
    private Object office;
    private Object companyId;
    private Object attachment;
    private Object attachments;
    private String value;
    private String label;
    private Object type;
    private Object description;
    private Object sort;
    private Object parentId;
    private List<OfficeListBean> officeList;

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public boolean isIsNewRecord() {
        return isNewRecord;
    }

    public void setIsNewRecord(boolean isNewRecord) {
        this.isNewRecord = isNewRecord;
    }

    public Object getRemarks() {
        return remarks;
    }

    public void setRemarks(Object remarks) {
        this.remarks = remarks;
    }

    public Object getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Object createDate) {
        this.createDate = createDate;
    }

    public Object getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Object updateDate) {
        this.updateDate = updateDate;
    }

    public Object getOffice() {
        return office;
    }

    public void setOffice(Object office) {
        this.office = office;
    }

    public Object getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Object companyId) {
        this.companyId = companyId;
    }

    public Object getAttachment() {
        return attachment;
    }

    public void setAttachment(Object attachment) {
        this.attachment = attachment;
    }

    public Object getAttachments() {
        return attachments;
    }

    public void setAttachments(Object attachments) {
        this.attachments = attachments;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Object getType() {
        return type;
    }

    public void setType(Object type) {
        this.type = type;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public Object getSort() {
        return sort;
    }

    public void setSort(Object sort) {
        this.sort = sort;
    }

    public Object getParentId() {
        return parentId;
    }

    public void setParentId(Object parentId) {
        this.parentId = parentId;
    }

    public List<OfficeListBean> getOfficeList() {
        return officeList;
    }

    public void setOfficeList(List<OfficeListBean> officeList) {
        this.officeList = officeList;
    }

    public static class OfficeListBean {
        /**
         * id : 7a66b30236334da8b58e0b1894780d8b
         * isNewRecord : false
         * remarks : null
         * createDate : null
         * updateDate : null
         * office : null
         * companyId : null
         * attachment : null
         * attachments : null
         * parentIds : null
         * name : 宝马区
         * sort : 30
         * area : null
         * code : null
         * type : 2
         * grade : null
         * address : null
         * zipCode : null
         * master : null
         * phone : null
         * fax : null
         * email : null
         * useable : null
         * bmsx : null
         * bmsxmc : null
         * primaryPerson : null
         * deputyPerson : null
         * childDeptList : null
         * tzqsZt : null
         * parentId : 0
         */

        private String id;
        private boolean isNewRecord;
        private Object remarks;
        private Object createDate;
        private Object updateDate;
        private Object office;
        private Object companyId;
        private Object attachment;
        private Object attachments;
        private Object parentIds;
        private String name;
        private int sort;
        private Object area;
        private Object code;
        private String type;
        private Object grade;
        private Object address;
        private Object zipCode;
        private Object master;
        private Object phone;
        private Object fax;
        private Object email;
        private Object useable;
        private Object bmsx;
        private Object bmsxmc;
        private Object primaryPerson;
        private Object deputyPerson;
        private Object childDeptList;
        private Object tzqsZt;
        private String parentId;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public boolean isIsNewRecord() {
            return isNewRecord;
        }

        public void setIsNewRecord(boolean isNewRecord) {
            this.isNewRecord = isNewRecord;
        }

        public Object getRemarks() {
            return remarks;
        }

        public void setRemarks(Object remarks) {
            this.remarks = remarks;
        }

        public Object getCreateDate() {
            return createDate;
        }

        public void setCreateDate(Object createDate) {
            this.createDate = createDate;
        }

        public Object getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(Object updateDate) {
            this.updateDate = updateDate;
        }

        public Object getOffice() {
            return office;
        }

        public void setOffice(Object office) {
            this.office = office;
        }

        public Object getCompanyId() {
            return companyId;
        }

        public void setCompanyId(Object companyId) {
            this.companyId = companyId;
        }

        public Object getAttachment() {
            return attachment;
        }

        public void setAttachment(Object attachment) {
            this.attachment = attachment;
        }

        public Object getAttachments() {
            return attachments;
        }

        public void setAttachments(Object attachments) {
            this.attachments = attachments;
        }

        public Object getParentIds() {
            return parentIds;
        }

        public void setParentIds(Object parentIds) {
            this.parentIds = parentIds;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public Object getArea() {
            return area;
        }

        public void setArea(Object area) {
            this.area = area;
        }

        public Object getCode() {
            return code;
        }

        public void setCode(Object code) {
            this.code = code;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Object getGrade() {
            return grade;
        }

        public void setGrade(Object grade) {
            this.grade = grade;
        }

        public Object getAddress() {
            return address;
        }

        public void setAddress(Object address) {
            this.address = address;
        }

        public Object getZipCode() {
            return zipCode;
        }

        public void setZipCode(Object zipCode) {
            this.zipCode = zipCode;
        }

        public Object getMaster() {
            return master;
        }

        public void setMaster(Object master) {
            this.master = master;
        }

        public Object getPhone() {
            return phone;
        }

        public void setPhone(Object phone) {
            this.phone = phone;
        }

        public Object getFax() {
            return fax;
        }

        public void setFax(Object fax) {
            this.fax = fax;
        }

        public Object getEmail() {
            return email;
        }

        public void setEmail(Object email) {
            this.email = email;
        }

        public Object getUseable() {
            return useable;
        }

        public void setUseable(Object useable) {
            this.useable = useable;
        }

        public Object getBmsx() {
            return bmsx;
        }

        public void setBmsx(Object bmsx) {
            this.bmsx = bmsx;
        }

        public Object getBmsxmc() {
            return bmsxmc;
        }

        public void setBmsxmc(Object bmsxmc) {
            this.bmsxmc = bmsxmc;
        }

        public Object getPrimaryPerson() {
            return primaryPerson;
        }

        public void setPrimaryPerson(Object primaryPerson) {
            this.primaryPerson = primaryPerson;
        }

        public Object getDeputyPerson() {
            return deputyPerson;
        }

        public void setDeputyPerson(Object deputyPerson) {
            this.deputyPerson = deputyPerson;
        }

        public Object getChildDeptList() {
            return childDeptList;
        }

        public void setChildDeptList(Object childDeptList) {
            this.childDeptList = childDeptList;
        }

        public Object getTzqsZt() {
            return tzqsZt;
        }

        public void setTzqsZt(Object tzqsZt) {
            this.tzqsZt = tzqsZt;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }
    }
}
