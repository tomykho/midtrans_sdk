#import "MidtransSdkPlugin.h"
#import <MidtransKit/MidtransKit.h>

@interface MidtransSdkPlugin () <MidtransUIPaymentViewControllerDelegate>
@end

@implementation MidtransSdkPlugin
FlutterMethodChannel* channel;

+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  channel = [FlutterMethodChannel
      methodChannelWithName:@"midtrans_sdk"
            binaryMessenger:[registrar messenger]];
  MidtransSdkPlugin* instance = [[MidtransSdkPlugin alloc] init];
  [registrar addMethodCallDelegate:instance channel:channel];
}

- (void)handleMethodCall:(FlutterMethodCall*)call result:(FlutterResult)result {
  if ([@"init" isEqualToString:call.method]) {
    [self initWithCall:call result:result];
  }
  else if ([@"startPaymentUiFlow" isEqualToString:call.method]) {
    [self startPaymentUiFlowWithCall:call result:result];
  }
  else {
    result(FlutterMethodNotImplemented);
  }
}

- (UIViewController *)rootViewController {
  return [UIApplication sharedApplication].keyWindow.rootViewController;
}

- (void)initWithCall:(FlutterMethodCall*)call result:(FlutterResult)result {
    NSString* clientKey = call.arguments[@"clientKey"];
    NSString* merchantServerURL = call.arguments[@"merchantBaseUrl"];
    
    MidtransServerEnvironment environment = MidtransServerEnvironmentProduction;
    #ifdef DEBUG
    environment = MidtransServerEnvironmentSandbox;
    #endif
    [[MidtransConfig shared] setClientKey:clientKey
             environment:environment
       merchantServerURL:merchantServerURL];
    result(nil);
}

- (void)startPaymentUiFlowWithCall:(FlutterMethodCall*)call result:(FlutterResult)result {
    NSString* token = call.arguments[@"token"];
    [[MidtransMerchantClient shared] requestTransacationWithCurrentToken:token completion:^(MidtransTransactionTokenResponse * _Nullable regenerateToken, NSError * _Nullable error) {
        MidtransUIPaymentViewController *vc = [[MidtransUIPaymentViewController alloc] initWithToken:regenerateToken];
        vc.paymentDelegate = self;
        [[self rootViewController] presentViewController:vc animated:YES completion:nil];
    }];
    result(nil);
}

- (void)onTransactionFinished:(MidtransTransactionResult *)result canceled:(BOOL) isTransactionCanceled {
    NSMutableDictionary *arguments = [NSMutableDictionary dictionary];
    arguments[@"isTransactionCanceled"] = @(isTransactionCanceled);
    if (result != nil) {
        arguments[@"transactionStatus"] = result.transactionStatus;
        arguments[@"statusMessage"] = result.statusMessage;
        arguments[@"transactionId"] = result.transactionId;
        arguments[@"orderId"] = result.orderId;
        arguments[@"paymentType"] = result.paymentType;
    }
    [channel invokeMethod:@"onTransactionFinished" arguments:arguments];
}

#pragma mark - MidtransUIPaymentViewControllerDelegate
- (void)paymentViewController:(MidtransUIPaymentViewController *)viewController paymentDeny:(MidtransTransactionResult *)result {
    NSLog(@"paymentDeny: %@", result);
    [self onTransactionFinished:result canceled:NO];
}

- (void)paymentViewController:(MidtransUIPaymentViewController *)viewController paymentFailed:(NSError *)error {
    NSLog(@"paymentFailed: %@", error);
    [self onTransactionFinished:nil canceled:NO];
}

- (void)paymentViewController:(MidtransUIPaymentViewController *)viewController paymentPending:(MidtransTransactionResult *)result {
    NSLog(@"paymentPending: %@", result);
    [self onTransactionFinished:result canceled:NO];
}

- (void)paymentViewController:(MidtransUIPaymentViewController *)viewController paymentSuccess:(MidtransTransactionResult *)result {
    NSLog(@"paymentSuccess: %@", result);
    [self onTransactionFinished:result canceled:NO];
}

- (void)paymentViewController_paymentCanceled:(MidtransUIPaymentViewController *)viewController {
    NSLog(@"paymentCanceled");
    [self onTransactionFinished:nil canceled:YES];
}

@end
