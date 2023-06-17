import 'source-map-support/register';
import * as cdk from 'aws-cdk-lib';
import { CalculatorApiStack } from './stack/CalculatorApiStack';

const stackProps: cdk.StackProps = { 
  env: {
      account: "463935914557",
      region: "eu-west-1"
  }
};

const app = new cdk.App();

new CalculatorApiStack(app, 'CalculatorApi', stackProps);