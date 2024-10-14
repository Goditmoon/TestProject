<script setup>
    import { ref } from 'vue'
    import { ElMessage } from 'element-plus'
    import useUserInfoStore from '@/stores/userInfo.js'
    import { userPasswordUpdateService } from '@/api/user.js'
    import { useRouter } from 'vue-router' // 引入 useRouter

    const userInfoStore = useUserInfoStore()
    const router = useRouter() // 初始化 router

    // 用户信息
    const userInfo = ref({
        oldPassword: '',
        newPassword: '',
        confirmPassword: ''
    })

    // 表单验证规则
    const rules = {
        oldPassword: [
            { required: true, message: '请输入旧密码', trigger: 'blur' },
            { min: 5, max: 16, message: '密码长度在 5 到 16 个字符', trigger: 'blur' }
        ],
        newPassword: [
            { required: true, message: '请输入新密码', trigger: 'blur' },
            { min: 5, max: 16, message: '密码长度在 5 到 16 个字符', trigger: 'blur' }
        ],
        confirmPassword: [
            { required: true, message: '请输入确认密码', trigger: 'blur' },
            { min: 5, max: 16, message: '密码长度在 5 到 16 个字符', trigger: 'blur' },
            {
                validator: (rule, value, callback) => {
                    if (value !== userInfo.value.newPassword) {
                        callback(new Error('两次输入的密码不一致!'))
                    } else {
                        callback()
                    }
                },
                trigger: 'blur'
            }
        ]
    }

    // 密码显示控制
    const passwordVisible = ref(false)

    // 切换密码显示与隐藏
    const togglePasswordVisibility = () => {
        passwordVisible.value = !passwordVisible.value
    }

    // 获取表单的 ref
    const formRef = ref(null)

    // 重置密码
    const resetPassword = async () => {
        if (formRef.value) {
            formRef.value.validate(async (valid) => {
                if (valid) {
                    //调用接口
                    const params = {
                        old_pwd: userInfo.value.oldPassword,
                        new_pwd: userInfo.value.newPassword,
                        re_pwd: userInfo.value.confirmPassword
                    };
                    let result = await userPasswordUpdateService(params)
                    ElMessage.success(result.msg ? result.msg : '修改成功')
                    //修改pinia中的个人信息
                    userInfoStore.setInfo(userInfo.value)
                    // 清空表单
                    formRef.value.resetFields()
                    // 跳转到登录页面
                    router.push('/login')
                } else {
                    ElMessage.error('表单验证失败')
                    return false
                }
            })
        }
    }
</script>


<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>重置密码</span>
            </div>
        </template>
        <el-row>
            <el-col :span="12">
                <el-form ref="formRef" :model="userInfo" :rules="rules" label-width="100px" size="large">
                    <el-form-item label="旧密码" prop="oldPassword">
                        <el-input v-model="userInfo.oldPassword" type="password" placeholder="请输入原密码"></el-input>
                    </el-form-item>
                    <el-form-item label="新密码" prop="newPassword">
                        <el-input :type="passwordVisible ? 'text' : 'password'" v-model="userInfo.newPassword"
                            placeholder="新密码">
                            <template slot="append">
                                <el-button @click="togglePasswordVisibility">
                                    <i :class="passwordVisible ? 'el-icon-view' : 'el-icon-view-off'"></i>
                                </el-button>
                            </template>
                        </el-input>
                    </el-form-item>
                    <el-form-item label="确认密码" prop="confirmPassword">
                        <el-input :type="passwordVisible ? 'text' : 'password'" v-model="userInfo.confirmPassword"
                            placeholder="确保两次输入的密码一致"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="resetPassword">提交修改</el-button>
                    </el-form-item>
                </el-form>
            </el-col>
        </el-row>
    </el-card>
</template>