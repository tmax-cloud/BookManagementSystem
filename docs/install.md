
## Prerequisite

* gradle
* java11
* docker-compose
* kubectl
* nexus3 (1.빌드 준비 참고)
* istio


## 1. 빌드 준비

### Nexus 구성하기

1. Docker를 통해 로컬 서버로 실행하기 [(이미지 정보)](https://hub.docker.com/r/sonatype/nexus3/)

    ```bash
    docker run --rm --name nexus -d -p 18080:8081 -v /nexus-data:/nexus-data -u root sonatype/nexus3
    ```
   
2. http://localhost:18080에 접속 후 admin 비밀번호 설정하기
   - 최초 로그인 비밀번호는 `/nexus-data/admin.password`에서 확인
   
3. (optional) Deployment Policy를 Allow redeploy를 설정

   1. 화면 상단(GNB)의 톱니바퀴 클릭
   2. LNB에서 Repositories > Repositories 클릭
   3. Repository 목록에서 maven-releases 클릭
   4. Hosted > Deployment policy 드랍다운 메뉴에서 Allow redeploy 선택
   5. Save
   

5. build.gradle에서 common 빌드 설정에 nexus 정보 입력
    - subproject(common) > repositories.maven.credentials
      - URL
      - username, password 

## 2. 빌드 및 테스트

docker-compose를 이용해 로컬 환경에 구성하기

```bash
make install
```

* 이미지 빌드가 함께 실행됨
* 빌드될 이미지의 태그를 변경하려면 Makefile에 DOCKER_NAMESPACE, VERSIONTAG 변수를 수정

배포된 형상 내리기(docker-compose down)

```bash
make down
```

## 3. 이미지 푸시

```bash
make push-image REGISTRYSERVER=docker.io DOCKER_NAMESPACE=library
```

## 4. Kubernetes 환경에 배포

```bash
kubectl apply -k make/kustomize
```

## [Istio Telemetry Addons 설치](https://istio.io/latest/docs/tasks/observability/gateways/)

### Install Jaeger

```bash
kubectl apply -f https://raw.githubusercontent.com/istio/istio/release-1.12/samples/addons/jaeger.yaml
```

```bash
istioctl dashboard jaeger
```

### Install Prometheus

```bash
kubectl apply -f https://raw.githubusercontent.com/istio/istio/release-1.12/samples/addons/prometheus.yaml
```