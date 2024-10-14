<script setup>
    import {
        Edit,
        Delete
    } from '@element-plus/icons-vue'

    import { ref } from 'vue'

    //文章分类数据模型
    const categorys = ref([
        {
            "id": 3,
            "categoryName": "美食",
            "categoryAlias": "my",
            "createTime": "2023-09-02 12:06:59",
            "updateTime": "2023-09-02 12:06:59"
        },
        {
            "id": 4,
            "categoryName": "娱乐",
            "categoryAlias": "yl",
            "createTime": "2023-09-02 12:08:16",
            "updateTime": "2023-09-02 12:08:16"
        },
        {
            "id": 5,
            "categoryName": "军事",
            "categoryAlias": "js",
            "createTime": "2023-09-02 12:08:33",
            "updateTime": "2023-09-02 12:08:33"
        }
    ])

    //用户搜索时选中的分类id
    const categoryId = ref('')

    //用户搜索时选中的发布状态
    const state = ref('')

    //文章列表数据模型
    const articles = ref([
        {
            "id": 5,
            "title": "陕西旅游攻略",
            "content": "兵马俑,华清池,法门寺,华山...爱去哪去哪...",
            "coverImg": "https://big-event-gwd.oss-cn-beijing.aliyuncs.com/9bf1cf5b-1420-4c1b-91ad-e0f4631cbed4.png",
            "state": "草稿",
            "categoryId": 2,
            "createTime": "2023-09-03 11:55:30",
            "updateTime": "2023-09-03 11:55:30"
        },
        {
            "id": 5,
            "title": "陕西旅游攻略",
            "content": "兵马俑,华清池,法门寺,华山...爱去哪去哪...",
            "coverImg": "https://big-event-gwd.oss-cn-beijing.aliyuncs.com/9bf1cf5b-1420-4c1b-91ad-e0f4631cbed4.png",
            "state": "草稿",
            "categoryId": 2,
            "createTime": "2023-09-03 11:55:30",
            "updateTime": "2023-09-03 11:55:30"
        },
        {
            "id": 5,
            "title": "陕西旅游攻略",
            "content": "兵马俑,华清池,法门寺,华山...爱去哪去哪...",
            "coverImg": "https://big-event-gwd.oss-cn-beijing.aliyuncs.com/9bf1cf5b-1420-4c1b-91ad-e0f4631cbed4.png",
            "state": "草稿",
            "categoryId": 2,
            "createTime": "2023-09-03 11:55:30",
            "updateTime": "2023-09-03 11:55:30"
        },
    ])

    //分页条数据模型
    const pageNum = ref(1)//当前页
    const total = ref(20)//总条数
    const pageSize = ref(3)//每页条数

    //当每页条数发生了变化，调用此函数
    const onSizeChange = (size) => {
        pageSize.value = size
        articleList();
    }
    //当前页码发生变化，调用此函数
    const onCurrentChange = (num) => {
        pageNum.value = num
        articleList();
    }


    //回显文章分类
    import { articleCategoryListService, articleListService, articleAddService, articleUpdateService, articleDeleteService } from '@/api/article.js'
    const articleCategoryList = async () => {
        let result = await articleCategoryListService();

        categorys.value = result.data;
    }

    //获取文章列表数据
    const articleList = async () => {
        let params = {
            pageNum: pageNum.value,
            pageSize: pageSize.value,
            categoryId: categoryId.value ? categoryId.value : null,
            state: state.value ? state.value : null
        }
        let result = await articleListService(params);

        //渲染视图
        total.value = result.data.total;
        articles.value = result.data.items;

        //处理数据，给数据模型扩展一个属性categoryName,分类名称
        for (let i = 0; i < articles.value.length; i++) {
            let article = articles.value[i];
            for (let j = 0; j < categorys.value.length; j++) {
                if (article.categoryId == categorys.value[j].id) {
                    article.categoryName = categorys.value[j].categoryName;
                }
            }
        }
    }
    articleCategoryList();
    articleList();

    import { QuillEditor } from '@vueup/vue-quill'
    import '@vueup/vue-quill/dist/vue-quill.snow.css'
    import { Plus } from '@element-plus/icons-vue'

    //抽屉是否显示标题
    const title = ref('')
    //是否处于编辑状态
    const isEdit = ref(false)
    //控制抽屉是否显示
    const visibleDrawer = ref(false)
    //添加表单数据模型
    const articleModel = ref({
        title: '',
        categoryId: '',
        coverImg: '',
        content: '',
        state: ''
    })

    //导入token 
    import { useTokenStore } from '@/stores/token.js'
    const tokenStore = useTokenStore();

    //上传成功的回调函数
    const uploadSuccess = (result) => {
        articleModel.value.coverImg = result.data;
        console.log(result.data);
    }

    //展示添加文章抽屉
    const showAddDrawer = () => {
        visibleDrawer.value = true;
        isEdit.value = false;
        cleararticleModel();
        //清空Quill Editor内容
        if (this.$refs.quillEditor) {
            this.$refs.quillEditor.quill.setContents([]);
        }
    }

    //添加文章
    import { ElMessage } from 'element-plus'
    const addArticle = async (clickState) => {
        //把发布状态赋值给数据模型
        articleModel.value.state = clickState;

        //调用接口
        let result = await articleAddService(articleModel.value);

        ElMessage.success(result.msg ? result.msg : '添加成功');

        //调用获取所有文章的接口
        articleList();

        //让抽屉消失
        visibleDrawer.value = false;

        //刷新当前列表
        articleList();
    }

    //展示修改文章抽屉
    const showDrawer = (row) => {
        visibleDrawer.value = true;
        isEdit.value = true;
        //回显表单数据
        articleModel.value = {
            id: row.id,
            title: row.title,
            categoryId: row.categoryId,
            coverImg: row.coverImg,
            content: row.content,
            state: row.state
        }
    }

    //修改文章
    const updateArticle = async () => {

        //调用接口
        let result = await articleUpdateService(articleModel.value);

        ElMessage.success(result.msg ? result.msg : '修改成功');

        //调用获取所有文章的接口
        articleList();

        //让抽屉消失
        visibleDrawer.value = false;

        //刷新当前列表
        articleList();
    }
    //取消修改文章
    const cancelArticle = async () => {
        visibleDrawer.value = false;

        ElMessage.success('已取消');
        //刷新当前列表
        cleararticleModel();
    }

    //清空表单数据
    const cleararticleModel = () => {
        articleModel.value = {
            id: '',
            title: '',
            categoryId: '',
            coverImg: '',
            content: '',
            state: ''
        }

        //在模板中使用nextTick，等待组件渲染完成，再重置Quill Editor内容    
        nextTick(() => {
            articleModel.value.content = '';
            // 手动重置 Quill Editor 内容
            if (this.$refs.quillEditor) {
                this.$refs.quillEditor.quill.setContents([]);
            }
        })
    }

    //删除文章
    import { ElMessageBox } from 'element-plus'
    const deleteArticle = (row) => {
        //调用接口

        ElMessageBox.confirm(
            '确定删除该文章吗？',
            '温馨提示',
            {
                confirmButtonText: '确认',
                cancelButtonText: '取消',
                type: 'warning',
            }
        )
            .then(async () => {
                //调用接口
                let result = await articleDeleteService(row.id);
                ElMessage({
                    type: 'success',
                    message: '删除成功',
                })
                //刷新列表
                articleList();
            })
            .catch(() => {
                ElMessage({
                    type: 'info',
                    message: '已取消',
                })
            })
    }

</script>
<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>文章管理</span>
                <div class="extra">
                    <el-button type="primary"
                        @click="title = '添加文章',showAddDrawer();">添加文章</el-button>
                </div>
            </div>
        </template>
        <!-- 搜索表单 -->
        <el-form inline>
            <el-form-item label="文章分类：">
                <el-select placeholder="请选择" v-model="categoryId" style="width: 200px">
                    <el-option v-for="c in categorys" :key="c.id" :label="c.categoryName" :value="c.id">
                    </el-option>
                </el-select>
            </el-form-item>

            <el-form-item label="发布状态：">
                <el-select placeholder="请选择" v-model="state" style="width: 200px">
                    <el-option label="已发布" value="已发布"></el-option>
                    <el-option label="草稿" value="草稿"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="articleList">搜索</el-button>
                <el-button @click="categoryId = '', state = ''">重置</el-button>
            </el-form-item>
        </el-form>
        <!-- 文章列表 -->
        <el-table :data="articles" style="width: 100%">
            <el-table-column label="文章标题" width="400" prop="title"></el-table-column>
            <el-table-column label="分类" prop="categoryName"></el-table-column>
            <el-table-column label="发表时间" prop="createTime"> </el-table-column>
            <el-table-column label="状态" prop="state"></el-table-column>
            <el-table-column label="操作" width="100">
                <template #default="{ row }">
                    <el-button :icon="Edit" circle plain type="primary"
                        @click="title = '修改文章',showDrawer(row);"></el-button>
                    <el-button :icon="Delete" circle plain type="danger" @click="deleteArticle(row)"></el-button>
                </template>
            </el-table-column>
            <template #empty>
                <el-empty description="没有数据" />
            </template>
        </el-table>
        <!-- 分页条 -->
        <el-pagination v-model:current-page="pageNum" v-model:page-size="pageSize" :page-sizes="[3, 5 ,10, 15]"
            layout="jumper, total, sizes, prev, pager, next" background :total="total" @size-change="onSizeChange"
            @current-change="onCurrentChange" style="margin-top: 20px; justify-content: flex-end" />

        <!-- 抽屉 -->
        <el-drawer v-model="visibleDrawer" :title="title" direction="rtl" size="50%">
            <!-- 添加文章表单 -->
            <el-form :model="articleModel" label-width="100px">
                <el-form-item label="文章标题">
                    <el-input v-model="articleModel.title" placeholder="请输入标题"></el-input>
                </el-form-item>
                <el-form-item label="文章分类">
                    <el-select placeholder="请选择" v-model="articleModel.categoryId">
                        <el-option v-for="c in categorys" :key="c.id" :label="c.categoryName" :value="c.id">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="文章封面">
                    <!-- 
                    auto-upload: 设置是否自动上传
                    action: 设置服务器接口路径
                    name: 设置上传的文件字段名
                    headers: 设置上传的请求头
                    on-success: 设置上传成功的回调函数
                    -->
                    <el-upload class="avatar-uploader" :auto-upload="true" :show-file-list="false" action="/api/upload"
                        name="file" :headers="{'Authorization':tokenStore.token}" :on-success="uploadSuccess">
                        <img v-if="articleModel.coverImg" :src="articleModel.coverImg" class="avatar" />
                        <el-icon v-else class="avatar-uploader-icon">
                            <Plus />
                        </el-icon>
                    </el-upload>
                </el-form-item>
                <el-form-item label="文章内容">
                    <div class="editor">
                        <quill-editor ref="quillEditor" theme="snow" v-model:content="articleModel.content" contentType="html">
                        </quill-editor>
                    </div>
                </el-form-item>
                <el-form-item v-if="isEdit">
                    <el-button type="primary" @click="updateArticle">确认</el-button>
                    <el-button type="info" @click="cancelArticle">取消</el-button>
                </el-form-item>
                <el-form-item v-else>
                    <el-button type="primary" @click="addArticle('已发布')">发布</el-button>
                    <el-button type="info" @click="addArticle('草稿')">草稿</el-button>
                </el-form-item>
            </el-form>
        </el-drawer>
    </el-card>
</template>
<style lang="scss" scoped>
    .page-container {
        min-height: 100%;
        box-sizing: border-box;

        .header {
            display: flex;
            align-items: center;
            justify-content: space-between;
        }
    }

    /* 抽屉样式 */
    .avatar-uploader {
        :deep() {
            .avatar {
                width: 178px;
                height: 178px;
                display: block;
            }

            .el-upload {
                border: 1px dashed var(--el-border-color);
                border-radius: 6px;
                cursor: pointer;
                position: relative;
                overflow: hidden;
                transition: var(--el-transition-duration-fast);
            }

            .el-upload:hover {
                border-color: var(--el-color-primary);
            }

            .el-icon.avatar-uploader-icon {
                font-size: 28px;
                color: #8c939d;
                width: 178px;
                height: 178px;
                text-align: center;
            }
        }
    }

    .editor {
        width: 100%;

        :deep(.ql-editor) {
            min-height: 200px;
        }
    }
</style>