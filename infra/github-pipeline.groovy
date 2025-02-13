multibranchPipelineJob('seobe') {
    branchSources {
        github {
            id('1')
            repoOwner('slepimis120')
            repository('seobe')
            scanCredentialsId('GLOBAL_CREDENTIALS_ID')
        }
    }
    factory {
        workflowBranchProjectFactory {
            scriptPath('infra/Jenkinsfile')
        }
    }
    configure { node ->
        def source = node / sources / data / 'jenkins.branch.BranchSource' / source
        source / buildForkPRMerge << false
        source / buildOriginPRMerge << true
    }
}