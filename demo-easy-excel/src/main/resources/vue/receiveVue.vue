<template>

          <el-form-item>
            <el-button type="primary" @click="export2Excel">
              <icon-svg name="export"></icon-svg>导出
            </el-button>
          </el-form-item>
    <!--导出-->
    <export-to-excel
      ref="export2ExcelModal"
      :getDataList="getExportDateList"
      :columns="exportColumns"></export-to-excel>

</template>

<script>
  import { dateFormat, fmtMoney, queryDictionaryText } from "@/utils";
  import ExportToExcel from "@/components/excel/export-to-excel";

  export default {
    created() {
    },
    components: {
      ExportToExcel,
    },
    methods: {
      //导出至excel
      export2Excel() {
        //this.$refs.export2ExcelModal.open("路段收费报表");
        this.$http({
          url: this.$http.adornUrl("/rpt/test/export"),
          method: "post",
          responseType: "blob",
          data: this.$http.adornParams({
            startTime: dateFormat(new Date(this.dataForm.date[0]), 'yyyy-MM-dd HH:mm:ss'),
            endTime: dateFormat(new Date(this.dataForm.date[1]), 'yyyy-MM-dd HH:mm:ss'),
          }),
        })
          .then((res) => {
            if (!res) return;
            const blob = new Blob([res.data], {
              type: "application/vnd.ms-excel",
            }); // 构造一个blob对象来处理数据，并设置文件类型

            if (window.navigator.msSaveOrOpenBlob) {
              //兼容IE10
              navigator.msSaveBlob(blob, this.filename);
            } else {
              const href = URL.createObjectURL(blob); //创建新的URL表示指定的blob对象
              const a = document.createElement("a"); //创建a标签
              a.style.display = "none";
              a.href = href; // 指定下载链接
              a.download = this.filename; //指定下载文件名
              a.click(); //触发下载
              URL.revokeObjectURL(a.href); //释放URL对象
            }
          })
          .catch(() => {
            this.$message.error("网络异常,请稍后再试");
          });
      },
      getExportDateList(callback) {
        this.getDataList();
      },
    },
  };
</script>

<style>
  .el-range-editor--medium .el-range-separator {
    width: 24px;
  }
</style>
