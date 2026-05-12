<template>
  <div>
    <el-card>
      <div class="toolbar">
        <el-input v-model="keyword" placeholder="搜索项目名称" class="search-input" clearable @change="fetchData" />
        <el-button v-if="userStore.role==='admin'" type="primary" @click="openDialog()">发布项目进展</el-button>
      </div>
      <el-table :data="records" stripe>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="projectName" label="项目名称" width="180" />
        <el-table-column prop="content" label="进展内容" />
        <el-table-column prop="progressPercent" label="进度(%)" width="100" />
        <el-table-column prop="status" label="状态" width="100" />
        <el-table-column label="发布时间" width="170">
          <template #default="{row}">{{ formatTime(row.createTime) }}</template>
        </el-table-column>
        <el-table-column label="操作" width="220">
          <template #default="{row}">
            <el-button size="small" @click="showDetail(row)">查看</el-button>
            <template v-if="userStore.role==='admin'">
              <el-button size="small" @click="openDialog(row)">编辑</el-button>
              <el-button size="small" type="danger" @click="handleDelete(row.id)">删除</el-button>
            </template>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination v-model:current-page="page" :total="total" :page-size="10"
                     @current-change="fetchData" layout="total, prev, pager, next" />
    </el-card>

    <el-dialog v-model="dialogVisible" :title="isEdit?'编辑项目进展':'发布项目进展'" width="600px">
      <el-form ref="formRef" :model="form" :rules="rules">
        <el-form-item label="项目名称" prop="projectName"><el-input v-model="form.projectName" /></el-form-item>
        <el-form-item label="进展内容" prop="content"><el-input v-model="form.content" type="textarea" :rows="6" /></el-form-item>
        <el-form-item label="进度">
          <el-input-number v-model="form.progressPercent" :min="0" :max="100" style="width:100%" />
        </el-form-item>
        <el-form-item label="状态"><el-input v-model="form.status" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible=false">取消</el-button>
        <el-button type="primary" @click="handleSave">保存</el-button>
      </template>
    </el-dialog>

    <el-dialog v-model="detailVisible" title="项目进展详情" width="600px">
      <h2>{{ detail.projectName }}</h2>
      <p style="color:#999">进度：{{ detail.progressPercent }}% | 状态：{{ detail.status }}</p>
      <el-divider />
      <div>{{ detail.content }}</div>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useUserStore } from '@/stores/user'
import { getProjectProgresses, createProjectProgress, updateProjectProgress, deleteProjectProgress } from '@/api/notice'
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
const form = reactive({ projectName: '', content: '', progressPercent: 0, status: '' })
let editId = null

const rules = {
  projectName: [{ required: true, message: '请输入项目名称', trigger: 'blur' }],
  content: [{ required: true, message: '请输入进展内容', trigger: 'blur' }],
}

function formatTime(t) { return t ? t.replace('T', ' ').substring(0, 19) : '' }
async function fetchData() {
  const res = await getProjectProgresses({ page: page.value, pageSize: 10, keyword: keyword.value })
  records.value = res.data.records
  total.value = res.data.total
}

function openDialog(row) {
  if (row) {
    isEdit.value = true; editId = row.id
    form.projectName = row.projectName; form.content = row.content
    form.progressPercent = row.progressPercent; form.status = row.status
  } else {
    isEdit.value = false; editId = null
    form.projectName = ''; form.content = ''; form.progressPercent = 0; form.status = ''
  }
  dialogVisible.value = true
}

async function handleSave() {
  const valid = await formRef.value.validate().catch(() => false)
  if (!valid) return
  if (isEdit.value) { await updateProjectProgress(editId, form) }
  else { await createProjectProgress(form) }
  dialogVisible.value = false
  ElMessage.success(isEdit.value ? '更新成功' : '发布成功')
  fetchData()
}

async function handleDelete(id) {
  await ElMessageBox.confirm('确定删除？', '提示', { type: 'warning' })
  await deleteProjectProgress(id)
  ElMessage.success('已删除')
  fetchData()
}

function showDetail(row) { detail.value = row; detailVisible.value = true }

fetchData()
</script>
