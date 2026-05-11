<template>
  <div>
    <el-card>
      <div style="display:flex;justify-content:space-between;margin-bottom:16px">
        <el-input v-model="keyword" placeholder="搜索标题" style="width:240px" clearable @change="fetchData" />
        <el-button v-if="userStore.role==='admin'" type="primary" @click="openDialog()">发布公告</el-button>
      </div>
      <el-table :data="records" stripe>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="title" label="标题" />
        <el-table-column prop="createTime" label="发布时间" width="180" />
        <el-table-column label="操作" width="200">
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
                     @current-change="fetchData" layout="total, prev, pager, next" style="margin-top:16px" />
    </el-card>

    <el-dialog v-model="dialogVisible" :title="isEdit?'编辑公告':'发布公告'" width="600px">
      <el-form :model="form">
        <el-form-item label="标题"><el-input v-model="form.title" /></el-form-item>
        <el-form-item label="内容"><el-input v-model="form.content" type="textarea" :rows="6" /></el-form-item>
        <el-form-item label="置顶"><el-switch v-model="form.isTop" :active-value="1" :inactive-value="0" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible=false">取消</el-button>
        <el-button type="primary" @click="handleSave">保存</el-button>
      </template>
    </el-dialog>

    <el-dialog v-model="detailVisible" title="公告详情" width="600px">
      <h2>{{ detail.title }}</h2>
      <el-divider />
      <div>{{ detail.content }}</div>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useUserStore } from '@/stores/user'
import { getNotices, createNotice, updateNotice, deleteNotice } from '@/api/notice'
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
const form = reactive({ title: '', content: '', isTop: 0 })
let editId = null

async function fetchData() {
  const res = await getNotices({ page: page.value, pageSize: 10, keyword: keyword.value })
  records.value = res.data.records
  total.value = res.data.total
}

function openDialog(row) {
  if (row) {
    isEdit.value = true; editId = row.id
    form.title = row.title; form.content = row.content; form.isTop = row.isTop
  } else {
    isEdit.value = false; editId = null
    form.title = ''; form.content = ''; form.isTop = 0
  }
  dialogVisible.value = true
}

async function handleSave() {
  if (isEdit.value) { await updateNotice(editId, form) }
  else { await createNotice(form) }
  dialogVisible.value = false
  ElMessage.success(isEdit.value ? '更新成功' : '发布成功')
  fetchData()
}

async function handleDelete(id) {
  await ElMessageBox.confirm('确定删除？', '提示', { type: 'warning' })
  await deleteNotice(id)
  ElMessage.success('已删除')
  fetchData()
}

function showDetail(row) { detail.value = row; detailVisible.value = true }

fetchData()
</script>
