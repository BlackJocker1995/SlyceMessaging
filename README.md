# Slyce Messaging API

![](https://circleci.com/gh/snipsnap/SlyceMessaging.svg?style=shield&circle-token=46075f470208f71a4836c234126bb773c51219d8) [![](https://jitpack.io/v/Slyce-Inc/SlyceMessaging.svg)](https://jitpack.io/#Slyce-Inc/SlyceMessaging)


![](sample-photos/example.png?raw=true) ![](sample-photos/chat-with-image.png?raw=true)

Basic features of the API:

 * Full custimization of colors
 * Dynamic timestamps
 * Support for (optional) photo messages
 * Avatar photos (with onclick listeners)
 * Copy text with long press
 * Allows for more mesages to be loaded when user scrolls close to the top
 * Upon recieval of a message, a snackbar is shown which the user can use to scroll to the next message

## Installation

Add the following to your app's gradle file:

```ruby
repositories {
    jcenter()
    maven { url "https://s3.amazonaws.com/repo.commonsware.com" }
    maven { url "https://jitpack.io" }
}

dependencies {
    compile 'com.github.Slyce-Inc:SlyceMessaging:1.1.2'
}
```

## The API

You must initialize the fragment by declaring an XML tag like the following:

```xml
<fragment
            android:name="it.slyce.messaging.SlyceMessagingFragment"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:id="@+id/messaging_fragment"/>
```

### SlyceMessagingFragment

```java
public void setUserSendsMessageListener(UserSendsMessageListener listener); // gets called when the user sends a message
public void setUserClicksAvatarPhotoListener(UserClicksAvatarPhotoListener listener); // gets called when a user clicks an avatar photo. Optional.
public void setDefaultAvatarUrl(String url); // The avatar for the current user.
public void setDefaultUserId(String id); // A unique identifier for the current user.
public void setPictureButtonVisible(boolean bool); // Used to toggle whether the user can send picture messages. Default is true.
public void setStyle(int style); // See section "Custimize colors"
public void setMoreMessagesExist(boolean bool); // Sets whether more messages can be loaded from the top
public void setLoadMoreMessagesListener(ShouldLoadMoreMessagesListener listener); // Gets called when the user scrolls close to the top, if relevent

public void addMessage(Message message);
public void addMessages(List<Message> messages);
public void replaceMessages(List<MessageItems> messages);
```

### Message System
```java
public abstract class Message {
    public void setDate(String date);
	public void setSource(MessageSource source);
	public void setAvatarUrl(String url);
	public void setDisplayName(String name);
	public void setUserId(String id);
}

public class TextMessage extends Message {
	public void setText(String text);
}

public class MediaMessage extends Message{
	public void setPhotoUrl(String url);
}

public enum MessageSource {
	LOCAL_USER,
	EXTERNAL_USER
}
```

### Listeners
```java
public interface UserSendsMessageListener {
	public void onUserSendsTextMessage(String text);
	public void onUserSendsMediaMessage(Uri imageUri);
}

public interface UserClicksAvatarPictureListener {
	public void userClicksAvatarPhoto(MessageOrigin messageOrigin, String userId);
}

public interface LoadMoreMessagesListener {
    public List<Message> loadMoreMessages();
}
```

### General Messages
We now allow for messages to enter the feed that come from the app itself rather than one of the users. This message can also contain a sequence of options.

```java
public class GeneralTextMessage extends Message {
    public void setText(String text);
}

public class GeneralOptionsMessage extends Message {
    public void setTitle(String title);
    public void setOptions(String[] options);
    public void setOnOptionSelectedListener(OnOptionSelectedListener onOptionSelectedListener);
}

public interface OnOptionSelectedListener {
    String onOptionSelected(int optionSelected); 
            // Returns the string that should replace the title text after the options are removed.
}
```

### Custimize colors

You can custimize the colors of the fragment by providing a style with the following attributes in a method call to SlyceMessagingFragment. All attributes are colors.

* backgroundColor
* timestampTextColor
* localBubbleBackground
* localBubbleTextColor
* externalBubbleBackground
* externalBubbleTextColor
* snackbarBackground
* snackbarTitleColor
* snackbarButtonColor
## Inspired by JSQMessagesViewController

This library was inspired by [JSQMessagesViewController](https://github.com/jessesquires/JSQMessagesViewController), an excellent messages UI library for iOS.

Stay tuned -- we are joining forces with JSQ for the next generation of messages UI libraries: MessageKit for iOS AND Android! https://github.com/MessageKit/MessageKit-Android

## Developers

We have a local.properties file checked in for CI builds. Please do not check in changes to local.properties. Run this command to prevent that file from showing up as changed:

```git update-index --assume-unchanged local.properties```
