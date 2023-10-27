```

pipeline {
    agent any
    parameters {
        booleanParam(name: 'IS_BUILD', defaultValue:true, description: '项目是否需要重新构建？')
    }

    stages {
        stage('checkout') {
            steps {
                echo 'Gitea 拉取代码 Checkout'
                //拉取代码
                    script{
                   
                    println("${BRANCH_TAG}")	
                
                    checkout([$class: 'GitSCM', branches: [[name: "${BRANCH_TAG}"]], 
                              doGenerateSubmoduleConfigurations: false, 
                              extensions: [], 
                              submoduleCfg: [], 
                              userRemoteConfigs: [[credentialsId: '53bfaf91-bfa4-4c09-a537-3bb52b71fd35', 
                              url: "http://git.tetpark.com/fengbb/grayscale.git"]]])

                } 
               
            }
        }
        // 代码编译
        stage('build') {
            when {
                expression {
                    return (params.IS_BUILD == true)
                }
            }
            steps {
                echo 'Maven 开始构建 Building ......'
                // Run Maven on a Unix agent.
                sh '/usr/local/maven/bin/mvn clean package -Dmaven.test.skip=true'
            }

            post {
                // If Maven was able to run the tests, even if some of the test
                // failed, record the test results and archive the jar file.
                success {
                    echo 'ruoyi-admin Build Success!'
                }
            }
        }
        // 请选择需要部署的应用服务
        stage('choose-app') {
            steps {
                script {
                    def chooses = "${chooseDeployApp}"
                    echo "需要部署的应用服务, 选择结果如下：${chooseDeployApp}"
                    DEPLOY_FLAG = chooses.contains("ruoyi-admin") ? true : false
                    println "DEPLOY_FLAG:" + DEPLOY_FLAG
                }
            }
        }


        stage('ruoyi-admin') {
            when {
                expression {
                    return (DEPLOY_FLAG == true)
                }
            }
            steps {
                sshPublisher(
                        publishers: [sshPublisherDesc(
                                configName: "test_server_121",
                                transfers: [sshTransfer(
                                        cleanRemote: false,
                                        excludes: '',
                                        execCommand: 'sh /home/admin/app/ruoyi-admin/run_config_256m.sh backups_restart ruoyi-admin',
                                        execTimeout: 120000,
                                        flatten: false,
                                        makeEmptyDirs: false,
                                        noDefaultExcludes: false,
                                        patternSeparator: '[, ]+',
                                        remoteDirectory: 'app/ruoyi-admin/jar',
                                        remoteDirectorySDF: false,
                                        removePrefix: 'ruoyi-admin/target/',
                                        sourceFiles: 'ruoyi-admin/target/ruoyi-admin.jar')],
                                usePromotionTimestamp: false,
                                useWorkspaceInPromotion: false,
                                verbose: true)])

            }

            post {
                // If Maven was able to run the tests, even if some of the test
                // failed, record the test results and archive the jar file.
                success {
                    echo '部署 grayscale Success!'
                }
            }
        }

    }
}
```

