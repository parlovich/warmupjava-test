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
                maven: 'M3',
                jdk: 'jdk8',
                mavenLocalRepo: '.repository'
        ) {
            dir('warmupjava') {
                sh "mvn clean -DskipTests install"
            }
            dir('warmupjava-test') {
                sh 'mvn clean test'
                junit '**/target/surefire-reports/TEST-*.xml'
            }
        }
    }
}