function getUid() {
    var uid = $.cookie("uid");
    if (uid != null && uid.length >= 5) {
        return uid;
    }
    uid = (Math.random() * 0xffFF);
    uid <<= 16;
    uid |= (Math.random() * 0xffFF);
    uid = Math.abs(uid);
    uid = uid.toString(16);
    $.cookie("uid", uid, { "expires": 7 * 365 });
    return uid;
}
