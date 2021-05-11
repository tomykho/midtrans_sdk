#import "MidtransSdkPlugin.h"
#if __has_include(<midtrans_sdk/midtrans_sdk-Swift.h>)
#import <midtrans_sdk/midtrans_sdk-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "midtrans_sdk-Swift.h"
#endif

@implementation MidtransSdkPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftMidtransSdkPlugin registerWithRegistrar:registrar];
}
@end
