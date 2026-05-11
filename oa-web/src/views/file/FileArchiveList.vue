<template>
  <div>
    <el-card>
      <div style="display:flex;justify-content:space-between;margin-bottom:16px">
        <div>
          <el-input v-model="keyword" placeholder="搜索标题/文号" style="width:240px" clearable @change="fetchData" />
        </div>
        <el-button v-if="userStore.role==='admin' || userStore.role==='manager'" type="primary" @click="openDialog()">新增归档</el-button>
      </div>
      <el-table :data="records" stripe>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="fileNo" label="文号" width="160" />
        <el-table-column prop="title" label="标题" />
        <el-table-column prop="fileType" label="文件类型" width="120" />
        <el-table-column prop="keywords" label="关键词" width="150" />
        <el-table-column prop="archiveDate" label="归档日期" width="120" />
        <el-table-column label="操作" width="200">
          <template #default="{row}">
            <el-button size="small" @click="showDetail(row)">查看</el-button>
            <template v-if="userStore.role==='admin' || userStore.role==='manager'">
              <el-button size="small" @click="openDialog(row)">编辑</el-button>
              <el-button size="small" type="danger" @click="handleDelete(row.id)">删除</el-button>
            </template>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination v-model:current-page="page" :total="total" :page-size="10"
                     @current-change="fetchData" layout="total, prev, pager, next" style="margin-top:16px" />
    </el-card>

    <el-dialog v-model="dialogVisible" :title="isEdit?'编辑归档':'新增归档'" width="600px">
      <el-form :model="form">
        <el-form-item label="文号"><el-input v-model="form.fileNo" /></el-form-item>
        <el-form-item label="标题"><el-input v-model="form.title" /></el-form-item>
        <el-form-item label="文件类型"><el-input v-model="form.fileType" /></el-form-item>
        <el-form-item label="关键词"><el-input v-model="form.keywords" /></el-form-item>
        <el-form-item label="归档日期"><el-date-picker v-model="form.archiveDate" type="date" value-format="YYYY-MM-DD" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible=false">取消</el-button>
        <el-button type="primary" @click="handleSave">保存</el-button>
      </template>
    </el-dialog>

    <el-dialog v-model="detailVisible" title="归档详情" width="600px">
      <el-descriptions :column="2" border>
        <el-descriptions-item label="文号">{{ detail.fileNo }}</el-descriptions-item>
        <el-descriptions-item label="文件类型">{{ detail.fileType }}</el-descriptions-item>
        <el-descriptions-item label="标题" :span="2">{{ detail.title }}</el-descriptions-item>
        <el-descriptions-item label="关键词">{{ detail.keywords }}</el-descriptions-item>
        <el-descriptions-item label="归档日期">{{ detail.archiveDate }}</el-descriptions-item>
      </el-descriptions>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useUserStore } from '@/stores/user'
import { getFileArchives, createFileArchive, updateFileArchive, deleteFileArchive } from '@/api/file'
import { ElMessage, ElMessageBox } from 'element-plus'

const userStore = useUserStore()
const records = ref([])
const total = ref(0)
const page = ref(1)
const keyword = ref('')
const dialogVisible = ref(false)
const detailVisible = ref(false)
const isEdit = ref(false)
const detail = ref({})
const form = reactive({ fileNo: '', title: '', fileType: '', keywords: '', archiveDate: '' })
let editId = null

async function fetchData() {
  const res = await getFileArchives({ page: page.value, pageSize: 10, keyword: keyword.value })
  records.value = res.data.records
  total.value = res.data.total
}

function openDialog(row) {
  if (row) {
    isEdit.value = true; editId = row.id
    form.fileNo = row.fileNo; form.title = row.title; form.fileType = row.fileType
    form.keywords = row.keywords; form.archiveDate = row.archiveDate
  } else {
    isEdit.value = false; editId = null
    form.fileNo = ''; form.title = ''; form.fileType = ''; form.keywords = ''; form.archiveDate = ''
  }
  dialogVisible.value = true
}

async function handleSave() {
  if (isEdit.value) { await updateFileArchive(editId, form) }
  else { await createFileArchive(form) }
  dialogVisible.value = false
  ElMessage.success(isEdit.value ? '更新成功' : '新增成功')
  fetchData()
}

async function handleDelete(id) {
  await ElMessageBox.confirm('确定删除？', '提示', { type: 'warning' })
  await deleteFileArchive(id)
  ElMessage.success('已删除')
  fetchData()
}

function showDetail(row) { detail.value = row; detailVisible.value = true }

fetchData()
</script>
