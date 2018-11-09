node {
    env.JAVA_HOME = "${tool 'jdk8'}"
    env.PATH = "${env.JAVA_HOME}/bin:${env.PATH}"

    def mvnHome

    stage('Checkout') {
        cleanWs()
        checkout([$class           : 'GitSCM',
                  branches         : [[name: '*/master']],
                  userRemoteConfigs: [[url: '$REPO_URL']],
                  extensions       : [[$class: 'RelativeTargetDirectory', relativeTargetDir: 'warmupjava']]
        ])
        checkout([$class           : 'GitSCM',
                  branches         : [[name: '*/master']],
                  userRemoteConfigs: [[url: 'https://github.com/parlovich/warmupjava-test']],
                  extensions       : [[$class: 'RelativeTargetDirectory', relativeTargetDir: 'warmupjava-test']]
        ])
        mvnHome = tool 'M3'
    }
    stage('Test') {
        withMaven(
                // Maven installation declared in the Jenkins "Global Tool Configuration"
                maven: 'M3',
                // Maven settings.xml file defined with the Jenkins Config File Provider Plugin
                // Maven settings and global settings can also be defined in Jenkins Global Tools Configuration
                mavenSettingsConfig: 'my-maven-settings',
                mavenLocalRepo: '.repository') {

            // Run the maven build
            sh "mvn clean install"

        } // withMaven will discover the generated Maven artifacts, JUnit Surefire & FailSafe & FindBugs reports...

        dir('warmupjava') {
            sh "'${mvnHome}/bin/mvn' clean -DskipTests package"
            sh "'${mvnHome}/bin/mvn' org.apache.maven.plugins:maven-install-plugin:2.5.2:install-file -Dfile=target/warmup-java-1.0-SNAPSHOT.jar -DlocalRepositoryPath=../warmupjava-test/lib-maven"
        }
    }
    stage('Test') {
        dir('warmupjava-test') {
            sh "'${mvnHome}/bin/mvn' clean test"
            junit '**/target/surefire-reports/TEST-*.xml'
        }
    }
}