import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.placeholder
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v2019_2.projectFeatures.buildReportTab

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2020.2"

project {
    description = "Contains all other projects"
    defaultTemplate = RelativeId("RootTemplate")

    template(RootTemplate)

    features {
        buildReportTab {
            id = "PROJECT_EXT_1"
            title = "Code Coverage"
            startPage = "coverage.zip!index.html"
        }
    }

    cleanup {
        baseRule {
            preventDependencyCleanup = false
        }
    }

    subProject(id1)
}

object RootTemplate : Template({
    name = "rootTemplate"

    steps {
        script {
            id = "RUNNER_4"
            scriptContent = """echo "root build template""""
        }
        placeholder {
            id = "RUNNER_5"
        }
    }
})


object id1 : Project({
    name = "е1"

    buildType(id1_Rtyrty)
    buildType(id1_TestB1)

    template(id1_Template1)

    subProject(id1_Subproject)
})

object id1_Rtyrty : BuildType({
    name = "rtyrty"
})

object id1_TestB1 : BuildType({
    name = "TestB1"

    steps {
        script {
            id = "RUNNER_3"
            scriptContent = "echo 123"
        }
    }
})

object id1_Template1 : Template({
    name = "Template1"

    publishArtifacts = PublishMode.ALWAYS

    steps {
        script {
            name = "step1"
            id = "RUNNER_1"
            scriptContent = """echo "step1""""
        }
        script {
            name = "step2"
            id = "RUNNER_2"
            scriptContent = """echo "step 2""""
        }
    }
})


object id1_Subproject : Project({
    name = "subproject"
    defaultTemplate = RelativeId("id1_Template1")

    buildType(id1_Subproject_Dfg)
})

object id1_Subproject_Dfg : BuildType({
    name = "dfg"

    steps {
        script {
            name = "exec inginx"
            id = "RUNNER_6"
            scriptContent = """
                docker pull nginx
                docker exec -it nginx -t
            """.trimIndent()
        }
    }

    requirements {
        exists("docker.version", "RQ_1")
    }
})
