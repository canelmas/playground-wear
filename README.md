# playground-wear
Android Wear Showcase

# Content

1. Adding Wearable Features to Notifications
2. Wearable App
3. Custom UIs
4. Sending and Syncing Data
5. Watch Faces
6. Location


# TakeAways

## 1. Notifications
* Simple notifications, with actions or not, are shared with the wearable
* No background (default set to green)
* Notification icon shown on the wearable is by default app icon
* Implicit intents are triggered on the wearable and resolved on phone e.g. Show on Map action clicked on wearable asks app to resolve the intent
* Action triggered doesn't dismiss the notification on the wearable
* Notification dismissed on the phone also dismiss from wearable and vice versa
* By default each notification shared on wearable has 'Block App' action