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
### 1.1. Wearable Features
* Simple notifications, with actions or not, are shared with the wearable
* No background (default set to green)
* Notification icon shown on the wearable is by default app icon
* Implicit intents are triggered on the wearable and resolved on phone e.g. Show on Map action clicked on wearable asks app to resolve the intent
* Action triggered doesn't dismiss the notification on the wearable
* Notification dismissed on the phone also dismiss from wearable and vice versa
* By default each notification shared on wearable has 'Block App' action
* Once a wearable-only action is added, only these type of actions are displayed on wearable and they're not displayed on phone. 
* Phone actions are ignored on wearable if wearable-only action is added
* ContentIntent (intent when notification is clicked) also adds  'openOnPhone' action to wearable
* On wearable BigContent (BigStyle.bigText) is visible by default; on phone big view is visible when the notification is expanded.
* NotificationCompat.Builder.setLargeIcon() appears as large background images on wearable and they don't look good (scaled up to fit the wearable screen)
* WearableExtender.setBackground() should have 400x400 for non scrolling, 640x400 for backgrounds that support parallax scrolling. These bitmaps should be in **drawable-nodpi**
* Other non-bitmap resources for wearable notifications e.g. WearableExtender.setContentIcon(), should be in **drawable-hdpi**

### 1.2. Receiving Voice Input
* Voice Input should be added as notification action
* Use RemoteInput.getResultsFromInten() instead of Intent.getExtras() to obtain the voice result. Voice input is stored as ClipData.
* Pre-defined text responses (max 5), in addition to voice input can be set RemoteInput.setChoices()

### 1.3. Pages
* Extra pages are Notification instances, added via WearableExtender().addPage(notification) or addPages(notification:Collections)

### 1.4. Stacking Notifications
