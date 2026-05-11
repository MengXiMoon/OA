# 陶瓷素白 — 实施计划

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) to implement this plan task-by-task.

**Goal:** 用陶瓷素白主题完全替换 Element Plus 默认样式，保留组件功能逻辑

**Architecture:** 单文件 theme.css 全局覆盖全部 Element Plus CSS 变量和组件样式，6 个核心 Vue 文件重写模板和 scoped 样式，15 个 CRUD 视图清理内联样式

**Tech Stack:** Vue 3.5, Element Plus 2.14, 系统原生字体 PingFang SC / Microsoft YaHei

---

### Task 1: 创建 theme.css — 陶瓷素白全局样式系统

**Files:**
- Create: `oa-web/src/styles/theme.css`
- Modify: `oa-web/src/main.js`

- [ ] **Step 1: 创建 styles 目录并写入 theme.css**

```bash
mkdir -p oa-web/src/styles
```

Write `oa-web/src/styles/theme.css`:

```css
/* ===== Ceramic White Design System ===== */

:root {
  --bg-primary: #fcfcfc;
  --bg-surface: #ffffff;
  --bg-secondary: #f6f6f5;
  --border-color: #e2e4e3;
  --border-light: #eceeed;
  --text-primary: #1f1f1f;
  --text-secondary: #697077;
  --text-disabled: #a1a7ad;
  --color-primary: #1f1f1f;
  --color-primary-hover: #3d3d3d;
  --color-accent: #2563eb;
  --color-success: #0e6245;
  --color-warning: #8a4b0c;
  --color-danger: #b91c1c;
  --radius-sm: 3px;
  --radius-md: 6px;
  --radius-lg: 8px;
  --space-page: 20px 28px;
  --space-card: 20px;

  font-family: "PingFang SC", "Microsoft YaHei", "Helvetica Neue", sans-serif;
  font-size: 13px;
  line-height: 1.5;
  color: var(--text-primary);
  background: var(--bg-primary);
  -webkit-font-smoothing: antialiased;
}

*, *::before, *::after { box-sizing: border-box; }
body { margin: 0; background: var(--bg-primary); color: var(--text-primary); }

/* ===== Element Plus Variables ===== */

:root {
  --el-color-primary: var(--color-primary);
  --el-color-primary-light-3: var(--color-primary-hover);
  --el-color-primary-light-5: #555;
  --el-color-primary-light-7: #888;
  --el-color-primary-light-8: #aaa;
  --el-color-primary-light-9: #ccc;
  --el-color-primary-dark-2: #000;
  --el-color-success: var(--color-success);
  --el-color-warning: var(--color-warning);
  --el-color-danger: var(--color-danger);
  --el-bg-color: var(--bg-surface);
  --el-bg-color-page: var(--bg-primary);
  --el-bg-color-overlay: var(--bg-surface);
  --el-text-color-primary: var(--text-primary);
  --el-text-color-regular: var(--text-secondary);
  --el-text-color-secondary: var(--text-secondary);
  --el-text-color-placeholder: var(--text-disabled);
  --el-text-color-disabled: var(--text-disabled);
  --el-border-color-base: var(--border-color);
  --el-border-color-light: var(--border-light);
  --el-border-color-lighter: var(--border-light);
  --el-border-color-extra-light: var(--border-light);
  --el-border-color-dark: #c8cac9;
  --el-border-width: 0.5px;
  --el-border-radius-base: var(--radius-sm);
  --el-border-radius-small: 2px;
  --el-border-radius-round: var(--radius-md);
  --el-box-shadow-light: none;
  --el-box-shadow-dark: none;
  --el-box-shadow: none;
  --el-box-shadow-lighter: none;
}

/* ===== Icon Global Fix (prevents flex stretch to 183px) ===== */

.el-icon {
  width: 1em;
  height: 1em;
  flex-shrink: 0;
}
.el-icon svg {
  width: 1em;
  height: 1em;
  vertical-align: top;
}

/* ===== Card ===== */

.el-card {
  border: 0.5px solid var(--border-color) !important;
  border-radius: var(--radius-sm) !important;
  box-shadow: none !important;
  background: var(--bg-surface);
}
.el-card__header {
  border-bottom: 0.5px solid var(--border-color) !important;
  padding: 12px var(--space-card) !important;
  font-size: 14px;
  font-weight: 500;
  color: var(--text-primary);
}
.el-card__body { padding: var(--space-card) !important; }

/* ===== Table ===== */

.el-table {
  --el-table-border-color: var(--border-color);
  --el-table-header-bg-color: var(--bg-secondary);
  --el-table-row-hover-bg-color: rgba(0,0,0,0.02);
  --el-table-current-row-bg-color: rgba(0,0,0,0.04);
  --el-table-tr-bg-color: var(--bg-surface);
  border: 0.5px solid var(--border-color);
  border-radius: var(--radius-sm);
  font-size: 13px;
}
.el-table th.el-table__cell {
  background: var(--bg-secondary);
  color: var(--text-secondary);
  font-size: 12px;
  font-weight: 500;
  height: 36px;
  border-bottom: 0.5px solid var(--border-color);
}
.el-table td.el-table__cell {
  border-bottom: 0.5px solid var(--border-light);
  padding: 10px 16px;
}
.el-table--striped .el-table__body tr.el-table__row--striped td { background: var(--bg-surface); }
.el-table--border .el-table__cell { border-right: none; }
.el-table--border { border-left: none; border-right: none; }
.el-table__inner-wrapper::before { display: none; }

/* ===== Input / Form ===== */

.el-input__wrapper {
  border-radius: var(--radius-sm) !important;
  box-shadow: none !important;
  border: 0.5px solid var(--border-color);
  background: var(--bg-surface);
  transition: border-color 0.15s, box-shadow 0.15s;
}
.el-input__wrapper:hover { border-color: #c8cac9; }
.el-input.is-focus .el-input__wrapper {
  border-color: var(--color-primary);
  box-shadow: 0 0 0 3px rgba(0,0,0,0.05) !important;
}
.el-input__inner { color: var(--text-primary); font-size: 13px; }
.el-input__inner::placeholder { color: var(--text-disabled); }
.el-form-item__label { font-size: 13px; line-height: 32px; color: var(--text-primary); }
.el-form-item__content { line-height: 32px; }
.el-textarea__inner {
  border-radius: var(--radius-sm);
  border: 0.5px solid var(--border-color);
  box-shadow: none;
  font-size: 13px;
  color: var(--text-primary);
}
.el-textarea__inner:focus {
  border-color: var(--color-primary);
  box-shadow: 0 0 0 3px rgba(0,0,0,0.05);
}

/* ===== Select / DatePicker ===== */

.el-select .el-input.is-focus .el-input__wrapper {
  border-color: var(--color-primary);
  box-shadow: 0 0 0 3px rgba(0,0,0,0.05) !important;
}
.el-select-dropdown {
  background: var(--bg-surface);
  border: 0.5px solid var(--border-color);
  border-radius: var(--radius-md);
  box-shadow: none;
}
.el-select-dropdown__item {
  font-size: 13px;
  color: var(--text-primary);
  height: 32px;
  line-height: 32px;
  padding: 0 16px;
}
.el-select-dropdown__item.is-selected { color: var(--color-primary); font-weight: 500; }
.el-select-dropdown__item:hover { background: rgba(0,0,0,0.04); }

.el-picker-panel {
  background: var(--bg-surface);
  border: 0.5px solid var(--border-color);
  border-radius: var(--radius-md);
  box-shadow: none;
  color: var(--text-primary);
  font-size: 13px;
}
.el-date-picker__header-label { font-size: 13px; }
.el-date-table td { font-size: 12px; }
.el-date-table td.current:not(.disabled) .el-date-table-cell__text { background: var(--color-primary); color: #fff; }
.el-date-table td.today .el-date-table-cell__text { color: var(--color-primary); }

.el-popper {
  background: var(--bg-surface) !important;
  border: 0.5px solid var(--border-color);
  border-radius: var(--radius-md);
  box-shadow: none;
}

/* ===== Button ===== */

.el-button {
  --el-button-bg-color: var(--bg-surface);
  --el-button-border-color: var(--border-color);
  --el-button-text-color: var(--text-primary);
  --el-button-hover-bg-color: rgba(0,0,0,0.03);
  --el-button-hover-border-color: #c8cac9;
  --el-button-active-bg-color: rgba(0,0,0,0.06);
  border-radius: var(--radius-sm);
  font-weight: 500;
  font-size: 13px;
  box-shadow: none !important;
  height: 32px;
  padding: 0 14px;
}
.el-button--primary {
  --el-button-bg-color: var(--color-primary);
  --el-button-border-color: var(--color-primary);
  --el-button-text-color: #fff;
  --el-button-hover-bg-color: var(--color-primary-hover);
  --el-button-hover-border-color: var(--color-primary-hover);
  --el-button-active-bg-color: #000;
}
.el-button--danger {
  --el-button-bg-color: #fff;
  --el-button-border-color: var(--color-danger);
  --el-button-text-color: var(--color-danger);
  --el-button-hover-bg-color: #fef2f2;
  --el-button-hover-border-color: var(--color-danger);
}
.el-button--small { height: 24px; font-size: 12px; padding: 0 10px; }
.el-button.is-disabled { opacity: 0.4; }

/* ===== Dialog ===== */

.el-dialog {
  border-radius: var(--radius-lg) !important;
  border: 0.5px solid var(--border-color);
  box-shadow: none !important;
  max-width: 480px;
}
.el-dialog__header { padding: 16px 20px 12px; border-bottom: 0.5px solid var(--border-color); }
.el-dialog__title { font-size: 14px; font-weight: 500; color: var(--text-primary); }
.el-dialog__body { padding: 20px; }
.el-dialog__footer { padding: 12px 20px 16px; border-top: 0.5px solid var(--border-color); }
.el-overlay { background: rgba(0,0,0,0.15); }

/* ===== Message ===== */

.el-message {
  border-radius: var(--radius-sm);
  border: 0.5px solid var(--border-color);
  box-shadow: none;
  padding: 8px 14px;
  width: fit-content !important;
  max-width: 360px;
}
.el-message.is-center { justify-content: flex-start; }
.el-message .el-message__icon { font-size: 14px !important; width: 1em; height: 1em; flex-shrink: 0; margin-right: 6px; }
.el-message .el-message__icon svg { width: 1em; height: 1em; }
.el-message .el-message__content { font-size: 13px; color: var(--text-primary); }
.el-message--success { border-color: var(--color-success); }
.el-message--success .el-message__icon { color: var(--color-success); }
.el-message--error { border-color: var(--color-danger); }
.el-message--error .el-message__icon { color: var(--color-danger); }
.el-message--warning { border-color: var(--color-warning); }
.el-message--warning .el-message__icon { color: var(--color-warning); }

/* ===== MessageBox (confirm dialog) ===== */

.el-message-box {
  border-radius: var(--radius-lg);
  border: 0.5px solid var(--border-color);
  box-shadow: none !important;
  width: 360px !important;
  max-width: 90vw;
}
.el-message-box__header { padding: 16px 20px 12px; border-bottom: 0.5px solid var(--border-color); }
.el-message-box__title { font-size: 14px; font-weight: 500; }
.el-message-box__content { padding: 20px; font-size: 13px; }
.el-message-box__status { font-size: 14px !important; }
.el-message-box__btns { padding: 12px 20px 16px; border-top: 0.5px solid var(--border-color); }

/* ===== Pagination ===== */

.el-pagination {
  --el-pagination-bg-color: var(--bg-surface);
  --el-pagination-button-bg-color: var(--bg-surface);
  --el-pagination-hover-color: var(--text-primary);
  margin-top: 16px;
  justify-content: flex-end;
}
.el-pagination .el-pager li {
  border-radius: var(--radius-sm);
  font-size: 13px;
}
.el-pagination .el-pager li.is-active { background: var(--color-primary); color: #fff; }
.el-pagination button { border-radius: var(--radius-sm); }

/* ===== Tabs ===== */

.el-tabs__item { font-size: 13px; color: var(--text-secondary); }
.el-tabs__item.is-active { color: var(--text-primary); font-weight: 500; }
.el-tabs__active-bar { background: var(--color-primary); height: 1.5px; }

/* ===== Menu (Sidebar) ===== */

.el-menu { border-right: none !important; background: transparent; }
.el-menu-item,
.el-sub-menu__title {
  height: 36px;
  line-height: 36px;
  margin: 2px 8px;
  border-radius: var(--radius-md);
  font-size: 13px;
  color: var(--text-primary);
  transition: background-color 0.12s;
}
.el-menu-item:hover,
.el-sub-menu__title:hover { background: rgba(0,0,0,0.04) !important; }
.el-menu-item.is-active {
  background: rgba(0,0,0,0.08) !important;
  color: var(--text-primary) !important;
  font-weight: 500;
}
.el-sub-menu .el-menu { background: transparent; }
.el-sub-menu .el-menu-item { padding-left: 52px !important; height: 32px; line-height: 32px; }

/* ===== Descriptions ===== */

.el-descriptions { --el-descriptions-table-border: 0.5px solid var(--border-color); }
.el-descriptions__label { font-size: 12px; color: var(--text-secondary); }
.el-descriptions__content { font-size: 13px; color: var(--text-primary); }

/* ===== Dropdown / Tag ===== */

.el-dropdown-menu {
  border-radius: var(--radius-md);
  border: 0.5px solid var(--border-color);
  box-shadow: none;
}
.el-dropdown-menu__item:hover { background: rgba(0,0,0,0.04); }
.el-tag { --el-tag-border-radius: var(--radius-sm); font-size: 12px; }

/* ===== Checkbox / Radio ===== */

.el-checkbox__input.is-checked .el-checkbox__inner { background: var(--color-primary); border-color: var(--color-primary); }
.el-radio__input.is-checked .el-radio__inner { background: var(--color-primary); border-color: var(--color-primary); }

/* ===== Dialog Form ===== */

.el-dialog .el-form-item { margin-bottom: 14px; }
.el-dialog .el-form-item__label { font-size: 13px; }
.el-dialog .el-input__inner { font-size: 13px; }

/* ===== Toolbar Utilities ===== */

.toolbar { display: flex; justify-content: space-between; align-items: center; margin-bottom: 16px; gap: 12px; flex-wrap: wrap; }
.search-input { width: 220px; }
.el-card + .el-card { margin-top: 16px; }
```

- [ ] **Step 2: 修改 main.js — 替换 CSS import**

在 `oa-web/src/main.js` 中，将第 3 行：
```js
import 'element-plus/dist/index.css'
```
替换为：
```js
import './styles/theme.css'
```

- [ ] **Step 3: 构建验证**

```bash
cd oa-web && npx vite build 2>&1 | grep "✓ built"
```
期望：构建成功。

- [ ] **Step 4: 提交**

```bash
cd c:/Users/LENOVO/IdeaProjects/OA
mkdir -p oa-web/src/styles
git add oa-web/src/styles/theme.css oa-web/src/main.js
git commit -m "feat: 添加陶瓷素白全局主题 CSS，替换 Element Plus 默认样式"
```

---

### Task 2: 重写 Layout.vue — 纯 div 布局

**Files:**
- Modify: `oa-web/src/views/layout/Layout.vue`

- [ ] **Step 1: 完整替换 Layout.vue**

```vue
<template>
  <div class="layout">
    <header class="layout-header">
      <Navbar />
    </header>
    <div class="layout-body">
      <aside class="layout-aside">
        <Sidebar />
      </aside>
      <main class="layout-main">
        <router-view />
      </main>
    </div>
  </div>
</template>

<script setup>
import Sidebar from '@/components/common/Sidebar.vue'
import Navbar from '@/components/common/Navbar.vue'
</script>

<style scoped>
.layout { min-height: 100vh; background: var(--bg-primary); }
.layout-header {
  position: sticky; top: 0; z-index: 100;
  height: 48px; background: #fff;
  border-bottom: 0.5px solid var(--border-color);
}
.layout-body { display: flex; }
.layout-aside {
  position: sticky; top: 48px;
  width: 200px; height: calc(100vh - 48px);
  background: var(--bg-secondary);
  border-right: 0.5px solid var(--border-color);
  overflow-y: auto; flex-shrink: 0;
}
.layout-main {
  flex: 1; min-width: 0;
  min-height: calc(100vh - 48px);
  padding: var(--space-page);
  max-width: 960px; margin: 0 auto;
}
</style>
```

- [ ] **Step 2: 构建 + 提交**

```bash
cd oa-web && npx vite build 2>&1 | grep "✓ built"
cd c:/Users/LENOVO/IdeaProjects/OA
git add oa-web/src/views/layout/Layout.vue
git commit -m "feat: 重构布局为纯 div + CSS，陶瓷素白配色"
```

---

### Task 3: 重写 Sidebar + Navbar

**Files:**
- Modify: `oa-web/src/components/common/Sidebar.vue`
- Modify: `oa-web/src/components/common/Navbar.vue`

- [ ] **Step 1: 重写 Sidebar.vue**

```vue
<template>
  <div class="sidebar">
    <el-menu :default-active="route.path" router>
      <el-menu-item index="/dashboard"><el-icon><HomeFilled /></el-icon><span>首页</span></el-menu-item>
      <el-sub-menu index="notice">
        <template #title><el-icon><Bell /></el-icon><span>信息发布</span></template>
        <el-menu-item index="/notice">公告栏</el-menu-item>
        <el-menu-item index="/company-event">公司活动</el-menu-item>
        <el-menu-item index="/project-progress">项目进度</el-menu-item>
        <el-menu-item index="/weekly-report">公司周报</el-menu-item>
      </el-sub-menu>
      <el-sub-menu index="file">
        <template #title><el-icon><Folder /></el-icon><span>文件管理</span></template>
        <el-menu-item index="/incoming-file">收文管理</el-menu-item>
        <el-menu-item index="/outgoing-file">发文管理</el-menu-item>
        <el-menu-item index="/file-archive">档案管理</el-menu-item>
      </el-sub-menu>
      <el-menu-item index="/task"><el-icon><List /></el-icon><span>工作任务</span></el-menu-item>
      <el-sub-menu index="approval">
        <template #title><el-icon><DocumentChecked /></el-icon><span>审批申请</span></template>
        <el-menu-item index="/leave">请假申请</el-menu-item>
        <el-menu-item index="/travel">出差申请</el-menu-item>
        <el-menu-item index="/attendance">出勤记录</el-menu-item>
      </el-sub-menu>
      <el-menu-item index="/work-log"><el-icon><Edit /></el-icon><span>工作日志</span></el-menu-item>
      <el-sub-menu index="meeting">
        <template #title><el-icon><Calendar /></el-icon><span>会议管理</span></template>
        <el-menu-item index="/meeting-room">会议室管理</el-menu-item>
        <el-menu-item index="/meeting">会议管理</el-menu-item>
      </el-sub-menu>
      <el-menu-item v-if="userStore.role==='admin'" index="/system"><el-icon><Setting /></el-icon><span>系统管理</span></el-menu-item>
    </el-menu>
    <div class="sidebar-footer">OA v1.0</div>
  </div>
</template>

<script setup>
import { useRoute } from 'vue-router'
import { useUserStore } from '@/stores/user'
const route = useRoute()
const userStore = useUserStore()
</script>

<style scoped>
.sidebar { display: flex; flex-direction: column; height: 100%; padding: 8px 0; }
.sidebar :deep(.el-menu) { flex: 1; }
.sidebar-footer {
  padding: 10px 20px; border-top: 0.5px solid var(--border-color);
  font-size: 11px; color: var(--text-disabled);
}
</style>
```

- [ ] **Step 2: 重写 Navbar.vue**

```vue
<template>
  <div class="navbar">
    <div class="navbar-brand">
      <span class="navbar-logo">OA</span>
      <span class="navbar-title">办公系统</span>
    </div>
    <div class="navbar-right">
      <span class="navbar-role">{{ roleName }}</span>
      <span class="navbar-sep"></span>
      <el-dropdown trigger="click">
        <span class="navbar-user">{{ userStore.userInfo?.realName || '用户' }} <el-icon><ArrowDown /></el-icon></span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="handleLogout"><el-icon><SwitchButton /></el-icon>退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
const router = useRouter()
const userStore = useUserStore()
const roleName = computed(() => {
  const map = { admin: '管理员', manager: '部门经理', employee: '普通员工' }
  return map[userStore.role] || ''
})
function handleLogout() { userStore.logout(); router.push('/login') }
</script>

<style scoped>
.navbar { display: flex; justify-content: space-between; align-items: center; height: 48px; padding: 0 20px; }
.navbar-brand { display: flex; align-items: center; gap: 8px; }
.navbar-logo { font-size: 15px; font-weight: 600; color: var(--text-primary); letter-spacing: -0.01em; }
.navbar-title { font-size: 12px; color: var(--text-secondary); }
.navbar-right { display: flex; align-items: center; gap: 8px; height: 48px; }
.navbar-role { font-size: 12px; color: var(--text-secondary); }
.navbar-sep { width: 0.5px; height: 16px; background: var(--border-color); }
.navbar-user { display: flex; align-items: center; gap: 4px; cursor: pointer; font-size: 13px; color: var(--text-primary); padding: 2px 4px; border-radius: var(--radius-sm); transition: background-color 0.12s; }
.navbar-user:hover { background: rgba(0,0,0,0.04); }
.navbar-user .el-icon { font-size: 10px; color: var(--text-disabled); }
</style>
```

- [ ] **Step 3: 构建 + 提交**

```bash
cd oa-web && npx vite build 2>&1 | grep "✓ built"
cd c:/Users/LENOVO/IdeaProjects/OA
git add oa-web/src/components/common/Sidebar.vue oa-web/src/components/common/Navbar.vue
git commit -m "feat: 重写侧边栏和顶栏，移除旧版深色硬编码配色"
```

---

### Task 4: 重写 Login.vue

**Files:**
- Modify: `oa-web/src/views/login/Login.vue`

- [ ] **Step 1: 完整替换**

```vue
<template>
  <div class="login">
    <div class="login-panel">
      <div class="login-header">
        <h1 class="login-brand">OA</h1>
        <p class="login-desc">办公自动化系统</p>
      </div>
      <el-form :model="form" :rules="rules" ref="formRef" class="login-form">
        <el-form-item prop="username">
          <el-input v-model="form.username" placeholder="用户名" :prefix-icon="User" @keyup.enter="focusPassword" />
        </el-form-item>
        <el-form-item prop="password">
          <el-input ref="pwdRef" v-model="form.password" type="password" placeholder="密码" :prefix-icon="Lock" show-password @keyup.enter="handleLogin" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" class="login-btn" :loading="loading" @click="handleLogin">登 录</el-button>
        </el-form-item>
      </el-form>
      <div class="login-divider"><span>或</span></div>
      <el-button class="login-register" @click="showRegister = true">注册新账号</el-button>
    </div>
    <el-dialog v-model="showRegister" title="注册" width="400px" align-center>
      <el-form :model="regForm" label-position="top">
        <el-form-item label="用户名"><el-input v-model="regForm.username" /></el-form-item>
        <el-form-item label="密码"><el-input v-model="regForm.password" type="password" /></el-form-item>
        <el-form-item label="真实姓名"><el-input v-model="regForm.realName" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showRegister = false">取消</el-button>
        <el-button type="primary" @click="handleRegister">注册</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { ElMessage } from 'element-plus'
import { User, Lock } from '@element-plus/icons-vue'

const router = useRouter()
const userStore = useUserStore()
const formRef = ref(null)
const pwdRef = ref(null)
const form = reactive({ username: 'admin', password: '123456' })
const loading = ref(false)
const showRegister = ref(false)
const regForm = reactive({ username: '', password: '', realName: '' })

const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
}

function focusPassword() { pwdRef.value?.focus() }

async function handleLogin() {
  const valid = await formRef.value.validate().catch(() => false)
  if (!valid) return
  loading.value = true
  try {
    await userStore.doLogin(form.username, form.password)
    ElMessage({ message: '登录成功', type: 'success', duration: 1500 })
    setTimeout(() => router.push('/'), 200)
  } finally { loading.value = false }
}

async function handleRegister() {
  await userStore.doRegister(regForm.username, regForm.password, regForm.realName)
  showRegister.value = false
  ElMessage({ message: '注册成功', type: 'success', duration: 1500 })
  setTimeout(() => router.push('/'), 200)
}
</script>

<style scoped>
.login { display: flex; justify-content: center; align-items: center; min-height: 100vh; background: var(--bg-primary); }
.login-panel { width: 320px; }
.login-header { text-align: center; margin-bottom: 28px; }
.login-brand { font-size: 28px; font-weight: 600; color: var(--text-primary); margin: 0 0 4px; letter-spacing: -0.01em; }
.login-desc { font-size: 12px; color: var(--text-disabled); margin: 0; }
.login-form { margin-bottom: 18px; }
.login-form :deep(.el-form-item) { margin-bottom: 12px; }
.login-btn { width: 100%; height: 36px; font-size: 14px; letter-spacing: 0.06em; }
.login-divider { display: flex; align-items: center; gap: 12px; margin-bottom: 18px; }
.login-divider::before, .login-divider::after { content: ''; flex: 1; height: 0.5px; background: var(--border-color); }
.login-divider span { font-size: 12px; color: var(--text-disabled); }
.login-register { width: 100%; height: 36px; font-size: 13px; }
</style>
```

- [ ] **Step 2: 构建 + 提交**

```bash
cd oa-web && npx vite build 2>&1 | grep "✓ built"
cd c:/Users/LENOVO/IdeaProjects/OA
git add oa-web/src/views/login/Login.vue
git commit -m "feat: 重写登录页，陶瓷素白编辑排版风格"
```

---

### Task 5: 重写 Dashboard.vue

**Files:**
- Modify: `oa-web/src/views/dashboard/Dashboard.vue`

- [ ] **Step 1: 完整替换**

```vue
<template>
  <div class="dashboard">
    <div class="greeting">
      <h1 class="greeting-text">{{ greeting }}，{{ userStore.userInfo?.realName }}</h1>
      <p class="greeting-date">{{ today }}</p>
    </div>
    <div class="stat-grid">
      <div class="stat-card" v-for="item in stats" :key="item.label">
        <div class="stat-icon"><el-icon :size="14"><component :is="item.icon" /></el-icon></div>
        <div class="stat-body">
          <span class="stat-number">{{ item.value }}</span>
          <span class="stat-label">{{ item.label }}</span>
        </div>
      </div>
    </div>
    <div class="welcome-card">
      <h3 class="welcome-title">工作台</h3>
      <p class="welcome-text">当前角色：{{ roleName }}。从这里开始你的一天，所有模块均可从左侧导航栏访问。</p>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useUserStore } from '@/stores/user'
import { Bell, List, DocumentChecked, Calendar } from '@element-plus/icons-vue'

const userStore = useUserStore()

const roleName = computed(() => {
  const map = { admin: '管理员', manager: '部门经理', employee: '普通员工' }
  return map[userStore.role] || ''
})

const greeting = computed(() => {
  const h = new Date().getHours()
  if (h < 12) return '早上好'
  if (h < 18) return '下午好'
  return '晚上好'
})

const today = computed(() => new Date().toLocaleDateString('zh-CN', { year:'numeric', month:'long', day:'numeric', weekday:'long' }))

const stats = ref([
  { label: '公告', value: 12, icon: Bell },
  { label: '任务', value: 5, icon: List },
  { label: '待审批', value: 3, icon: DocumentChecked },
  { label: '会议', value: 5, icon: Calendar },
])
</script>

<style scoped>
.dashboard { max-width: 720px; }
.greeting { margin-bottom: 24px; }
.greeting-text { font-size: 20px; font-weight: 500; color: var(--text-primary); margin: 0 0 4px; }
.greeting-date { font-size: 12px; color: var(--text-disabled); margin: 0; }
.stat-grid { display: grid; grid-template-columns: repeat(4, 1fr); gap: 12px; margin-bottom: 20px; }
.stat-card {
  display: flex; align-items: flex-start; gap: 10px; padding: 16px;
  background: var(--bg-surface); border: 0.5px solid var(--border-color);
  border-radius: var(--radius-sm); cursor: default;
  transition: border-color 0.15s, background 0.15s;
}
.stat-card:hover { border-color: var(--text-primary); background: rgba(0,0,0,0.005); }
.stat-icon { display: flex; align-items: center; justify-content: center; width: 28px; height: 28px; border-radius: var(--radius-sm); background: rgba(0,0,0,0.04); color: var(--text-primary); flex-shrink: 0; }
.stat-body { display: flex; flex-direction: column; }
.stat-number { font-size: 22px; font-weight: 500; color: var(--text-primary); line-height: 1.1; }
.stat-label { font-size: 12px; color: var(--text-secondary); margin-top: 2px; }
.welcome-card { padding: 20px; background: var(--bg-surface); border: 0.5px solid var(--border-color); border-radius: var(--radius-sm); }
.welcome-title { font-size: 14px; font-weight: 500; color: var(--text-primary); margin: 0 0 6px; }
.welcome-text { font-size: 13px; color: var(--text-secondary); margin: 0; line-height: 1.6; }
@media (max-width: 900px) { .stat-grid { grid-template-columns: repeat(2, 1fr); } }
</style>
```

- [ ] **Step 2: 构建 + 提交**

```bash
cd oa-web && npx vite build 2>&1 | grep "✓ built"
cd c:/Users/LENOVO/IdeaProjects/OA
git add oa-web/src/views/dashboard/Dashboard.vue
git commit -m "feat: 重写仪表盘，陶瓷素白统计卡片"
```

---

### Task 6: 清理 15 个 CRUD 视图内联样式

**Files:** 所有 `oa-web/src/views/` 下的 `*List.vue`（15 个）

- [ ] **Step 1: 对每个文件执行相同的 3 个编辑**

对每个 `*List.vue`：
1. `<div style="display:flex;justify-content:space-between;margin-bottom:16px">` → `<div class="toolbar">`
2. 搜索 `el-input` 上的 `style="width:240px"` → `class="search-input"`
3. 移除 `el-pagination` 上的 `style="margin-top:16px"`

文件列表：
- `notice/NoticeList.vue`, `notice/CompanyEventList.vue`, `notice/ProjectProgressList.vue`, `notice/WeeklyReportList.vue`
- `file/IncomingFileList.vue`, `file/OutgoingFileList.vue`, `file/FileArchiveList.vue`
- `task/TaskList.vue`
- `approval/LeaveList.vue`, `approval/TravelList.vue`, `approval/AttendanceList.vue`
- `log/WorkLogList.vue`
- `meeting/MeetingRoomList.vue`, `meeting/MeetingList.vue`
- `system/UserList.vue`

- [ ] **Step 2: 构建 + 提交**

```bash
cd oa-web && npx vite build 2>&1 | grep "✓ built"
cd c:/Users/LENOVO/IdeaProjects/OA
git add oa-web/src/views/
git commit -m "feat: 清理 CRUD 视图内联样式，提取为 CSS 工具类"
```

---

### Task 7: 最终验证

- [ ] **Step 1: 生产构建**

```bash
cd oa-web && npx vite build 2>&1
```
期望：零错误。

- [ ] **Step 2: 目视检查**

启动 `npx vite --host 0.0.0.0 --port 5173`，登录 admin/123456，检查：
- 登录页：陶瓷白背景，OA 标题，无 el-card
- 仪表盘：问候语，4 列统计卡片，0.5px 边框
- 任意列表页：表头 #f6f6f5，墨色按钮
- 弹窗：8px 圆角，max-width 480px
- 消息通知：fit-content 宽度，14px 图标
- 侧边栏图标：13px（不是 183px）
- 侧边栏背景：#f6f6f5（不是 #304156）
- 刷新后登录状态保持

- [ ] **Step 3: 验证通过后提交**

```bash
git add -A
git commit -m "chore: 陶瓷素白主题最终验证通过"
```

---

## 自检

1. Spec 覆盖：配色 ✓、字体（系统原生）✓、布局 ✓、Card/Table/Form/Button/Dialog/Message 组件 ✓、图标 1em 约束 ✓、消息框 fit-content ✓、弹出面板背景 ✓、登录页 ✓、仪表盘 ✓
2. 无 TBD/TODO，所有代码完整
3. 所有 CSS 变量名一致，无冲突
