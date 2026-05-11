# 陶瓷素白 — OA 系统前端视觉重设计

**日期**: 2026-05-12  
**方向**: 陶瓷素白，有灰阶的白  
**约束**: 系统原生字体，不依赖 Google Fonts

---

## 一、设计方向

白色极简，用极浅灰阶区分层级。色彩退场，让信息说话。

**关键取舍**：
- 不用 box-shadow，全部用 0.5px 细线
- 不用 Google Fonts，只用系统字体
- 不用毛玻璃，陶瓷感是实心的、温润的
- 彩色只用于状态标签，日常界面以灰阶为主

---

## 二、配色

| 角色 | CSS 变量 | 色值 |
|------|----------|------|
| 页底色 | `--bg-primary` | `#fcfcfc` |
| 卡片/内容 | `--bg-surface` | `#ffffff` |
| 侧边栏/表头 | `--bg-secondary` | `#f6f6f5` |
| 分割线 | `--border-color` | `#e2e4e3` |
| 分割线浅 | `--border-light` | `#eceeed` |
| 主文字 | `--text-primary` | `#1f1f1f` |
| 次级文字 | `--text-secondary` | `#697077` |
| 弱文字 | `--text-disabled` | `#a1a7ad` |
| 主色 | `--color-primary` | `#1f1f1f` |
| 主色 hover | `--color-primary-hover` | `#3d3d3d` |
| 强调 | `--color-accent` | `#2563eb` |
| 成功 | `--color-success` | `#0e6245` |
| 警告 | `--color-warning` | `#8a4b0c` |
| 危险 | `--color-danger` | `#b91c1c` |
| 圆角 | `--radius-sm` | `3px` |
| 圆角中 | `--radius-md` | `6px` |
| 圆角大 | `--radius-lg` | `8px` |

---

## 三、字体

```css
font-family: "PingFang SC", "Microsoft YaHei", "Helvetica Neue", sans-serif;
```

层级：20px(页面标题) / 14px(卡片标题) / 13px(正文) / 12px(辅助)。无衬线体标题，统一用 body 字体不做区分。

---

## 四、布局

- Header: 48px sticky，白底 `#fff`，底部 0.5px 线
- Sidebar: 200px sticky，`#f6f6f5`，右侧 0.5px 线
- Main: 居中文档流，max-width 960px，padding 20px 28px
- 纯 div 布局，不用 el-container

---

## 五、核心组件

### Card
0.5px 边框，3px 圆角，无阴影。header 14px/500，底部 0.5px 线

### Table
0.5px 外框，3px 圆角。表头 `#f6f6f5`，12px/500，36px 高。行底 0.5px 浅线

### Input / Form
32px 高，0.5px 边框，3px 圆角。focus: `border-color: #1f1f1f`，`box-shadow: 0 0 0 3px rgba(0,0,0,0.05)`。label 13px，line-height 32px

### Button
32px 高，3px 圆角，13px 字。primary `bg: #1f1f1f`，default `border: 0.5px`。无阴影

### Dialog
8px 圆角，0.5px 边框，max-width 480px。遮罩 `rgba(0,0,0,0.15)` 无 blur

### Icon 全局约束
`.el-icon { width: 1em; height: 1em; flex-shrink: 0 }`  
`svg { width: 1em; height: 1em }`

### 消息框
`width: fit-content; max-width: 360px`

### 弹出面板
picker / select / popper 全部显式 `background: #fff`

---

## 六、技术方案

**策略**：一个 theme.css 文件覆盖全部 Element Plus 变量 + 组件样式。用 `!important` 只在与 Element Plus 硬编码冲突处使用。

**涉及文件**：
- 新建：`oa-web/src/styles/theme.css`
- 修改：`main.js`（替换 import）、`Layout.vue`、`Sidebar.vue`、`Navbar.vue`、`Login.vue`、`Dashboard.vue`
- 批量清理：15 个 CRUD 视图的内联 style

---

## 七、自检

- 无 TBD / TODO
- 配色/字体/间距内部一致
- 上次踩坑已记录：图标 1em 约束、消息框 fit-content、弹出面板 backdrop、系统字体
- 范围仅前端样式
