
node
{
  stage('scm')
   {  
      git 'https://github.com/komuravellyshivakumardevops/openmrs-core.git'
   }
  stage('artifact creation')
   {
      sh 'mvn package'
   }
}
      