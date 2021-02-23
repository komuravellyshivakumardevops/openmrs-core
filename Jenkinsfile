node
{
stage('scm')
  {
    git branch: 'feature1', url: 'https://github.com/komuravellyshivakumardevops/openmrs-core.git'
  }
stage('junit test results')
  {
     junit '/surefire-reports/*.xml'
  }
}
