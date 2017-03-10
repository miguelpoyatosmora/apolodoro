#!/bin/bash

echo "Running integration tests"
mvn test-compile failsafe:integration-test failsafe:verify -T 8
