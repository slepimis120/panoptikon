pipelineJob('seobe'){
    triggers{
        githubPush()
    }
    definition{
        cpsScm{
            scm{
                git {
                    remote{
                        url('git@github.com:slepimis120/seobe.git')
                    }
                    branches("*/*")
                }
            }
            scriptPath('infra/Jenkinsfile')
        }
    }
}