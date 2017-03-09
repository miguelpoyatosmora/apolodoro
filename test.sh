#!/bin/bash

mvn test-compile failsafe:integration-test failsafe:verify -T 8
