import uiautomation as uia
import time
import win32gui, win32con
import pyscreenshot as shot
import io
import base64
def GetMessage(Item, msglist=None):
    msglist = msglist if msglist is not None else list()
    if len(Item.GetChildren()) == 0:
        msglist.append(Item.Name)
    else:
        for i in Item.GetChildren():
            GetMessage(i, msglist)
    return [i for i in msglist if i]
def GetQRCode():
    WechatWindows = uia.WindowControl(ClassName='WeChatMainWndForPC')
    WechatWindows.SetFocus()
    MsgList = WechatWindows.ListControl(Name='消息')
    data = GetMessage(MsgList.GetChildren()[-1])[2]
    WechatWindows.ButtonControl(Name="玩家二维码").Click(simulateMove=False)
    timer = 0
    while (data == GetMessage(MsgList.GetChildren()[-1])[2]):
        if (timer >= 100):
            return "FAILED"
        time.sleep(0.1)
        timer = timer + 1
    MsgList.GetChildren()[-1].EditControl(Name="舞萌DX / 中二节奏 登入二维码").Click(simulateMove=False)
    hwnd = win32gui.FindWindow("CefWebViewWnd", None)
    bbox = win32gui.GetWindowRect(hwnd)
    win32gui.SetWindowPos(hwnd, win32con.HWND_TOPMOST, 0, 0, 0, 0,\
    win32con.SWP_SHOWWINDOW|win32con.SWP_NOMOVE|win32con.SWP_NOSIZE)
    win32gui.SetWindowPos(hwnd, win32con.HWND_NOTOPMOST, 0, 0, 0, 0,\
    win32con.SWP_SHOWWINDOW|win32con.SWP_NOMOVE|win32con.SWP_NOSIZE)
    win32gui.BringWindowToTop(hwnd)
    time.sleep(3)
    img = shot.grab(bbox)
    imgByteArr = io.BytesIO()
    img.save(imgByteArr, format='JPEG')
    imgByteArr = imgByteArr.getvalue()
    return str(base64.b64encode(imgByteArr),"utf-8")

print(GetQRCode())