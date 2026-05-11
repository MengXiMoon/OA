<template>
  <div>
    <el-card>
      <div class="toolbar">
        <div>
          <el-input v-model="keyword" placeholder="搜索用户名" class="search-input" clearable @change="fetchData" />
        </div>
      </div>
      <el-table :data="records" stripe>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="username" label="用户名" />
        <el-table-column prop="realName" label="姓名" />
        <el-table-column prop="role" label="角色" width="120">
          <template #default="{row}">
            <el-select v-model="row.role" @change="handleRoleChange(row)" size="small">
              <el-option label="管理员" value="admin" />
              <el-option label="部门经理" value="manager" />
              <el-option label="普通员工" value="employee" />
            </el-select>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="180" />
      </el-table>
      <el-pagination v-model:current-page="page" :total="total" :page-size="10"
                     @current-change="fetchData" layout="total, prev, pager, next" />
    </el-card>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { getUsers, updateUser } from '@/api/system'
import { ElMessage } from 'element-plus'

const records = ref([])
const total = ref(0)
const page = ref(1)
const keyword = ref('')

async function fetchData() {
  const res = await getUsers({ page: page.value, pageSize: 10, keyword: keyword.value })
  records.value = res.data.records
  total.value = res.data.total
}

async function handleRoleChange(row) {
  await updateUser(row.id, { role: row.role })
  ElMessage.success('角色已更新')
}

fetchData()
</script>
