SHELL:=/bin/bash
BASEPATH=$(CURDIR)
BUILDPATH=$(BASEPATH)/make
MAKEPATH=$(BASEPATH)/make
# gradle parameters
GRADLECMD=$(shell which gradle)

# docker parameters
DOCKERCMD=$(shell which docker)
DOCKERBUILD=$(DOCKERCMD) build
DOCKERRMIMAGE=$(DOCKERCMD) rmi
DOCKERPULL=$(DOCKERCMD) pull
DOCKERIMAGES=$(DOCKERCMD) images
DOCKERSAVE=$(DOCKERCMD) save
DOCKERCOMPOSECMD=$(shell which docker-compose)
DOCKERTAG=$(DOCKERCMD) tag
DOCKERRUN=$(DOCKERCMD) run

DOCKERCOMPOSEFILEPATH=$(MAKEPATH)
DOCKERCOMPOSEFILENAME=docker-compose.yml

DOCKERCOMPOSE_FILE_OPT=-f $(DOCKERCOMPOSEFILEPATH)/$(DOCKERCOMPOSEFILENAME)

REGISTRYSERVER=
DOCKER_NAMESPACE=tmaxcloudck
VERSIONTAG=0.0.1

# pull/push image
PUSHSCRIPTPATH=$(MAKEPATH)
PUSHSCRIPTNAME=pushimage.sh
REGISTRYUSER=tmaxcloudck
REGISTRYPASSWORD=tmax@cloud


CORE=core
BINPATH_CORE=$(BASEPATH)/$(CORE)/build/libs
DOCKERFILEPATH_CORE=$(BUILDPATH)/$(CORE)
DOCKERFILENAME_CORE=Dockerfile
DOCKERIMAGENAME_CORE=$(DOCKER_NAMESPACE)/book-$(CORE)

RATING=rating
BINPATH_RATING=$(BASEPATH)/$(RATING)/build/libs
DOCKERFILEPATH_RATING=$(BUILDPATH)/$(RATING)
DOCKERFILENAME_RATING=Dockerfile
DOCKERIMAGENAME_RATING=$(DOCKER_NAMESPACE)/book-$(RATING)

ORDER=order
BINPATH_ORDER=$(BASEPATH)/$(ORDER)/build/libs
DOCKERFILEPATH_ORDER=$(BUILDPATH)/$(ORDER)
DOCKERFILENAME_ORDER=Dockerfile
DOCKERIMAGENAME_ORDER=$(DOCKER_NAMESPACE)/book-$(ORDER)

PAYMENT=payment
BINPATH_PAYMENT=$(BASEPATH)/$(PAYMENT)/build/libs
DOCKERFILEPATH_PAYMENT=$(BUILDPATH)/$(PAYMENT)
DOCKERFILENAME_PAYMENT=Dockerfile
DOCKERIMAGENAME_PAYMENT=$(DOCKER_NAMESPACE)/book-$(PAYMENT)

DB=db
DOCKERFILEPATH_DB=$(BUILDPATH)/$(DB)
DOCKERFILENAME_DB=Dockerfile
DOCKERIMAGENAME_DB=$(DOCKER_NAMESPACE)/book-$(DB)

build: build-core build-rating build-order build-payment build-db

build-common:
	@echo "build jar for common..."
	@$(GRADLECMD) common:build

build-core:
	@echo "build jar for core..."
	@$(GRADLECMD) core:build
	cp $(BINPATH_CORE)/* $(DOCKERFILEPATH_CORE)
	@echo "build container for core..."
	$(DOCKERBUILD) -f $(DOCKERFILEPATH_CORE)/$(DOCKERFILENAME_CORE) -t $(DOCKERIMAGENAME_CORE):$(VERSIONTAG) .
	@echo "Done."

build-rating:
	@echo "build jar for rating..."
	@$(GRADLECMD) rating:build
	cp $(BINPATH_RATING)/* $(DOCKERFILEPATH_RATING)
	@echo "build container for rating..."
	$(DOCKERBUILD) -f $(DOCKERFILEPATH_RATING)/$(DOCKERFILENAME_RATING) -t $(DOCKERIMAGENAME_RATING):$(VERSIONTAG) .
	@echo "Done."

build-order:
	@echo "build jar for order..."
	@$(GRADLECMD) order:build
	cp $(BINPATH_ORDER)/* $(DOCKERFILEPATH_ORDER)
	@echo "build container for order..."
	$(DOCKERBUILD) -f $(DOCKERFILEPATH_ORDER)/$(DOCKERFILENAME_ORDER) -t $(DOCKERIMAGENAME_ORDER):$(VERSIONTAG) .
	@echo "Done."

build-payment:
	@echo "build jar for payment..."
	@$(GRADLECMD) payment:build
	cp $(BINPATH_PAYMENT)/* $(DOCKERFILEPATH_PAYMENT)
	@echo "build container for payment..."
	$(DOCKERBUILD) -f $(DOCKERFILEPATH_PAYMENT)/$(DOCKERFILENAME_PAYMENT) -t $(DOCKERIMAGENAME_PAYMENT):$(VERSIONTAG) .
	@echo "Done."

build-db:
	@echo "build container for db..."
	$(DOCKERBUILD) -f $(DOCKERFILEPATH_DB)/$(DOCKERFILENAME_DB) -t $(DOCKERIMAGENAME_DB):$(VERSIONTAG) .
	@echo "Done."

prepare-build: build-common
	@echo "start nexus..."
	$(DOCKERRUN) --rm --name nexus -d -p 18080:8081 -v /nexus-data:/nexus-data -u root sonatype/nexus3
	@echo "prepare common lib..."
	@$(GRADLECMD) common:publish

.PHONY: push-image
push-image:
	@echo "pusing image"
	@$(DOCKERTAG) $(DOCKER_IMAGE_NAME):$(VERSIONTAG) $(REGISTRYSERVER)$(DOCKER_IMAGE_NAME):$(VERSIONTAG)
	@$(PUSHSCRIPTPATH)/$(PUSHSCRIPTNAME) $(REGISTRYSERVER)$(DOCKER_IMAGE_NAME):$(VERSIONTAG) \
		$(REGISTRYUSER) $(REGISTRYPASSWORD) $(REGISTRYSERVER)
	@$(DOCKERRMIMAGE) $(REGISTRYSERVER)$(DOCKER_IMAGE_NAME):$(VERSIONTAG)

.PHONY: start
start:
	@echo "loading book msa images..."
	@$(DOCKERCOMPOSECMD) $(DOCKERCOMPOSE_FILE_OPT) up -d
	@echo "Start complete."

down:
	@echo "stoping book msa instance..."
	@$(DOCKERCOMPOSECMD) $(DOCKERCOMPOSE_FILE_OPT) down -v
	@echo "Done."

restart: down start

install: build start

clean:
	@$(GRADLECMD) core:clean
	@$(GRADLECMD) rating:clean
	@$(GRADLECMD) order:clean
	@$(GRADLECMD) payment:clean