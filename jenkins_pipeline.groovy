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
    stage('Build') {
        sh 'echo $JAVA_HOME'
        dir('warmupjava') {
            sh "'${mvnHome}/bin/mvn' clean -DskipTests package"
        }
    }
    stage('Test') {
        dir('warmupjava-test') {
            sh "'${mvnHome}/bin/mvn' clean test"
            junit '**/target/surefire-reports/TEST-*.xml'
        }
    }
}