import * as cdk from 'aws-cdk-lib';
import { Construct } from 'constructs';
import * as lambda from 'aws-cdk-lib/aws-lambda';
import * as api from 'aws-cdk-lib/aws-apigateway';

export class CalculatorApiStack extends cdk.Stack {
  constructor(scope: Construct, id: string, props?: cdk.StackProps) {
    super(scope, id, props);

    this.exportDeploymentEnv(props!);

    const restFunction = this.createLambdaFunctionRest();
    this.createApiRest(restFunction);
  }

  private createLambdaFunctionRest(): lambda.Function {
    const path = '../target/calculator.jar';

    const func = new lambda.Function(this, 'rest-function', {
      functionName: `${this.stackName}-rest-function`,
      runtime: lambda.Runtime.JAVA_17,
      handler: 'com.tools.handlers.StreamHandler',
      code: lambda.Code.fromAsset(path),
      timeout: cdk.Duration.seconds(20),
    });
    return func;
  }

  private createApiRest(apiFunction: lambda.Function) {
    const rest = new api.LambdaRestApi(this, 'rest-api', {
      restApiName: `${this.stackName}-APIs`,
      handler: apiFunction,
      integrationOptions: {
        proxy: false,
        passthroughBehavior: api.PassthroughBehavior.WHEN_NO_MATCH,
        requestTemplates: {
            "application/json": "$input.json('$')"
        },
        integrationResponses: [{
          statusCode: "200",
          responseTemplates: {
            "application/json": "$input.json('$')"
          }
        }]
      },
      proxy: false,
      deployOptions: {
        loggingLevel: api.MethodLoggingLevel.INFO
      },
    });

    const methodResponse: api.MethodResponse = {
      statusCode: "200", 
    }

    const resourceName = 'calculator';
    const calculator = rest.root.addResource(resourceName);
    calculator.addMethod('POST').addMethodResponse(methodResponse);

    new cdk.CfnOutput(this, 'api-endpoint', {
      value: `https://${rest.restApiId}.execute-api.${this.region}.amazonaws.com/prod/${resourceName}`,
      exportName: 'APIGatewayEndpoint'
    });

    return rest;
  }
  private exportDeploymentEnv(props: cdk.StackProps) {
    new cdk.CfnOutput(this, 'deployment-account', {
      value: props.env?.account!,
      exportName: 'DeploymentAccount'
    });

    new cdk.CfnOutput(this, 'deployment-region', {
      value: props.env?.region!,
      exportName: 'DeploymentRegion'
    })
  }
}
