<template>
  <div>
    <el-card>
      <div class="toolbar">
        <div>
          <el-input v-model="keyword" placeholder="搜索标题/文号" class="search-input" clearable @change="fetchData" />
        </div>
        <el-button v-if="userStore.role==='admin' || userStore.role==='manager'" type="primary" @click="openDialog()">新增发文</el-button>
      </div>
      <el-table :data="records" stripe>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="fileNo" label="文号" width="160" />
        <el-table-column prop="title" label="标题" />
        <el-table-column prop="sendToOrg" label="发往单位" width="160" />
        <el-table-column label="发文日期" width="170">
          <template #default="{row}">{{ formatTime(row.sendDate) }}</template>
        </el-table-column>
        <el-table-column prop="fileType" label="文件类型" width="100" />
        <el-table-column prop="status" label="状态" width="100" />
        <el-table-column label="操作" width="220">
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
                     @current-change="fetchData" layout="total, prev, pager, next" />
    </el-card>

    <el-dialog v-model="dialogVisible" :title="isEdit?'编辑发文':'新增发文'" width="600px">
      <el-form ref="formRef" :model="form" :rules="rules">
        <el-form-item label="文号" prop="fileNo"><el-input v-model="form.fileNo" /></el-form-item>
        <el-form-item label="标题" prop="title"><el-input v-model="form.title" /></el-form-item>
        <el-form-item label="发往单位" prop="sendToOrg"><el-input v-model="form.sendToOrg" /></el-form-item>
        <el-form-item label="发文日期" prop="sendDate"><el-date-picker v-model="form.sendDate" type="date" value-format="YYYY-MM-DD" /></el-form-item>
        <el-form-item label="文件类型" prop="fileType"><el-input v-model="form.fileType" /></el-form-item>
        <el-form-item label="内容" prop="content"><el-input v-model="form.content" type="textarea" :rows="6" /></el-form-item>
        <el-form-item label="状态">
          <el-select v-model="form.status">
            <el-option label="草稿" value="草稿" />
            <el-option label="已发送" value="已发送" />
            <el-option label="已归档" value="已归档" />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible=false">取消</el-button>
        <el-button type="primary" @click="handleSave">保存</el-button>
      </template>
    </el-dialog>

    <el-dialog v-model="detailVisible" title="发文详情" width="600px">
      <el-descriptions :column="2" border>
        <el-descriptions-item label="文号">{{ detail.fileNo }}</el-descriptions-item>
        <el-descriptions-item label="发往单位">{{ detail.sendToOrg }}</el-descriptions-item>
        <el-descriptions-item label="标题" :span="2">{{ detail.title }}</el-descriptions-item>
        <el-descriptions-item label="发文日期">{{ detail.sendDate }}</el-descriptions-item>
        <el-descriptions-item label="文件类型">{{ detail.fileType }}</el-descriptions-item>
        <el-descriptions-item label="状态">{{ detail.status }}</el-descriptions-item>
        <el-descriptions-item label="内容" :span="2">{{ detail.content }}</el-descriptions-item>
      </el-descriptions>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useUserStore } from '@/stores/user'
import { getOutgoingFiles, createOutgoingFile, updateOutgoingFile, deleteOutgoingFile } from '@/api/file'
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
const formRef = ref(null)
const form = reactive({ fileNo: '', title: '', sendToOrg: '', sendDate: '', fileType: '', content: '', status: '草稿' })
let editId = null

const rules = {
  fileNo: [{ required: true, message: '请输入文号', trigger: 'blur' }],
  title: [{ required: true, message: '请输入标题', trigger: 'blur' }],
  sendToOrg: [{ required: true, message: '请输入发往单位', trigger: 'blur' }],
  sendDate: [{ required: true, message: '请选择发文日期', trigger: 'change' }],
  fileType: [{ required: true, message: '请输入文件类型', trigger: 'blur' }],
  content: [{ required: true, message: '请输入内容', trigger: 'blur' }],
}

function formatTime(t) { return t ? t.replace('T', ' ').substring(0, 19) : '' }

async function fetchData() {
  const res = await getOutgoingFiles({ page: page.value, pageSize: 10, keyword: keyword.value })
  records.value = res.data.records
  total.value = res.data.total
}

function openDialog(row) {
  if (row) {
    isEdit.value = true; editId = row.id
    form.fileNo = row.fileNo; form.title = row.title; form.sendToOrg = row.sendToOrg
    form.sendDate = row.sendDate; form.fileType = row.fileType; form.content = row.content; form.status = row.status
  } else {
    isEdit.value = false; editId = null
    form.fileNo = ''; form.title = ''; form.sendToOrg = ''; form.sendDate = ''
    form.fileType = ''; form.content = ''; form.status = '草稿'
  }
  dialogVisible.value = true
}

async function handleSave() {
  const valid = await formRef.value.validate().catch(() => false)
  if (!valid) return
  if (isEdit.value) { await updateOutgoingFile(editId, form) }
  else { await createOutgoingFile(form) }
  dialogVisible.value = false
  ElMessage.success(isEdit.value ? '更新成功' : '新增成功')
  fetchData()
}

async function handleDelete(id) {
  await ElMessageBox.confirm('确定删除？', '提示', { type: 'warning' })
  await deleteOutgoingFile(id)
  ElMessage.success('已删除')
  fetchData()
}

function showDetail(row) { detail.value = row; detailVisible.value = true }

fetchData()
</script>
