<template>
  <div>
    <el-card>
      <div class="toolbar">
        <el-input v-model="keyword" placeholder="搜索标题" class="search-input" clearable @change="fetchData" />
        <el-button v-if="userStore.role==='管理员'" type="primary" @click="openDialog()">发布公司事件</el-button>
      </div>
      <el-table :data="records" stripe>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="title" label="标题" />
        <el-table-column prop="eventDate" label="事件日期" width="140" />
        <el-table-column prop="location" label="地点" width="180" />
        <el-table-column label="发布时间" width="170">
          <template #default="{row}">{{ formatTime(row.createTime) }}</template>
        </el-table-column>
        <el-table-column label="操作" width="220">
          <template #default="{row}">
            <el-button size="small" @click="showDetail(row)">查看</el-button>
            <template v-if="userStore.role==='管理员'">
              <el-button size="small" @click="openDialog(row)">编辑</el-button>
              <el-button size="small" type="danger" @click="handleDelete(row.id)">删除</el-button>
            </template>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination v-model:current-page="page" :total="total" :page-size="10"
                     @current-change="fetchData" layout="total, prev, pager, next" />
    </el-card>

    <el-dialog v-model="dialogVisible" :title="isEdit?'编辑公司事件':'发布公司事件'" width="600px">
      <el-form ref="formRef" :model="form" :rules="rules">
        <el-form-item label="标题" prop="title"><el-input v-model="form.title" /></el-form-item>
        <el-form-item label="内容" prop="content"><el-input v-model="form.content" type="textarea" :rows="6" /></el-form-item>
        <el-form-item label="事件日期" prop="eventDate"><el-date-picker v-model="form.eventDate" type="date" placeholder="选择日期" value-format="YYYY-MM-DD" /></el-form-item>
        <el-form-item label="地点" prop="location"><el-input v-model="form.location" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible=false">取消</el-button>
        <el-button type="primary" @click="handleSave">保存</el-button>
      </template>
    </el-dialog>

    <el-dialog v-model="detailVisible" title="公司事件详情" width="600px">
      <h2>{{ detail.title }}</h2>
      <p style="color:#999">日期：{{ detail.eventDate }} | 地点：{{ detail.location }}</p>
      <el-divider />
      <div>{{ detail.content }}</div>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useUserStore } from '@/stores/user'
import { getCompanyEvents, createCompanyEvent, updateCompanyEvent, deleteCompanyEvent } from '@/api/notice'
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
const form = reactive({ title: '', content: '', eventDate: '', location: '' })
let editId = null

const rules = {
  title: [{ required: true, message: '请输入标题', trigger: 'blur' }],
  content: [{ required: true, message: '请输入内容', trigger: 'blur' }],
  eventDate: [{ required: true, message: '请选择事件日期', trigger: 'change' }],
  location: [{ required: true, message: '请输入地点', trigger: 'blur' }],
}

function formatTime(t) { return t ? t.replace('T', ' ').substring(0, 19) : '' }

async function fetchData() {
  const res = await getCompanyEvents({ page: page.value, pageSize: 10, keyword: keyword.value })
  records.value = res.data.records
  total.value = res.data.total
}

function openDialog(row) {
  if (row) {
    isEdit.value = true; editId = row.id
    form.title = row.title; form.content = row.content; form.eventDate = row.eventDate; form.location = row.location
  } else {
    isEdit.value = false; editId = null
    form.title = ''; form.content = ''; form.eventDate = ''; form.location = ''
  }
  dialogVisible.value = true
}

async function handleSave() {
  const valid = await formRef.value.validate().catch(() => false)
  if (!valid) return
  if (isEdit.value) { await updateCompanyEvent(editId, form) }
  else { await createCompanyEvent(form) }
  dialogVisible.value = false
  ElMessage.success(isEdit.value ? '更新成功' : '发布成功')
  fetchData()
}

async function handleDelete(id) {
  await ElMessageBox.confirm('确定删除？', '提示', { type: 'warning' })
  await deleteCompanyEvent(id)
  ElMessage.success('已删除')
  fetchData()
}

function showDetail(row) { detail.value = row; detailVisible.value = true }

fetchData()
</script>
