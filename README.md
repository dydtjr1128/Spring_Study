# Spring_Study




## 모노리틱 아키텍쳐(Monolithic Architecture)
<p>
  <img src="https://user-images.githubusercontent.com/19161231/54512207-c923cf00-4996-11e9-8b40-727eecd07b33.png" width="25%" align="left"/>
  <p>
  모노리틱 아키텍쳐는 전통적인 웹 시스템 개발 스타일의 아키텍쳐로 하나의 어플리케이션 내부에 모든것이 들어가 있는 '통짜구조' 이다.</br>
  </br>
  <ul>
    <li>장점 : 규모가 작은 애플리케이션에서는 배포가 용이하며 운영 및 관리가 상대적으로 쉽다.</li>
    <li>단점 : 통으로 돌아가기 때문에 크기가 크고, 빌드 및 배포 시간, 서버의 기동 시간이 오래 걸린다.</li>
  </ul>
  </p>
</p>
<br clear="left">
</br></br>

## 마이크로 서비스 아키텍쳐(Micro Service Architecture, MSA)

<p>
  <img src="https://user-images.githubusercontent.com/19161231/54512218-cd4fec80-4996-11e9-8ec9-6d5d6f92e064.png" width="50%"  align="left"/>
  <p>
  각각의 서비스를 API형태로 호출하며 각각 독립적인 war 파일로 개발되며 각각의 Tomcat 인스턴스위에서 동작한다.</br>
  애플리케이션 로직을 분리해서 여러개의 애플리케이션으로 나눠서 서비스화하고, 각 서비스별로 톰캣을 분산 배치한 것이 핵심</br>
  </br>
  <ul>
    <li>장점 : 서비스를 독립적으로 개발/배포/운영가능</li>
    <li>단점 : 데이터를 API 호출로만 통신하기 때문에 성능상 문제 발생 가능성 있음.</li>
  </ul>
  </p>
</p>
<br clear="left">
</br></br>

## 클라우드 기반 컴퓨팅 모델

- IaaS(Infrastructure as a service)
- PaaS(Platform as a Service)
- SaaS(Software as a Service)
![image](https://user-images.githubusercontent.com/19161231/54671593-8ea66780-4b39-11e9-80f9-eb5e25a5f4ae.png)

