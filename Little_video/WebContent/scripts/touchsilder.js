function TouchSilder(objName) {
    var obj = document.getElementById(objName);
    if (obj != null)
        this.cnt = obj;
}
TouchSilder.prototype.cnt = null;
TouchSilder.prototype._tcPT = { X: 0, Y: 0, cntX: 0, cntY: 0, stepX: 0, stepY: 0, iCancel: 0, delay: 3000 };
TouchSilder.prototype._onTouchMove = function () {
    if (this._tcPT.iCancel == -1) {
        return;
    }
    var Y = event.targetTouches[0].pageY;
    var X = event.targetTouches[0].pageX;
    this._tcPT.stepX = X - this._tcPT.X;
    this._tcPT.stepY = Y - this._tcPT.Y;
    if (this._tcPT.iCancel == 0) {
        var Xoffset = this._tcPT.X - X;
        var Yoffset = this._tcPT.Y - Y;
        if (Math.abs(Yoffset) > Math.abs(Xoffset)) {
            this._tcPT.iCancel = -1;
            return;
        }
        this._tcPT.iCancel = 1;
    }
    event.preventDefault();
    X = this._tcPT.cntX + this._tcPT.X - X;
    this.cnt.scrollLeft = X;

}
TouchSilder.prototype._onTouchEnd = function () {
    if (this._autoTimer == null && this._tcPT.delay >= 1000) {
        this._autoTimer = setInterval(function () { me.AutoSilder(); }, this._tcPT.delay);
    }
    if (this._tcPT.iCancel != 1)
        return;
    event.preventDefault();
    var cW = this.cnt.clientWidth;
    var px = this.cnt.scrollLeft % this.cnt.clientWidth;
    var rx = (this.cnt.scrollLeft - px) / this.cnt.clientWidth;
    var me = this;
    var endPx;
    if (this._tcPT.stepX == 0)
        return;
    if (this._tcPT.stepX < 0) { //rigth
        if (this.cnt.scrollLeft >= this.cnt.scrollWidth - this.cnt.clientWidth) {
            endPx = 0;
        }
        else if (px > cW * .2) {
            endPx = (rx + 1) * cW;
        }
        else {
            endPx = rx * cW;
        }
    }
    else {
        if (this.cnt.scrollLeft <= 0) {
            endPx = this.cnt.scrollWidth - this.cnt.clientWidth;
        }
        else if (px > cW * .8) {
            endPx = (rx + 1) * cW;
        }
        else {
            endPx = rx * cW;
        }
    }
    var func = function () { me.ScrollTo(endPx); }
    this.SetAutoScrollFunc(func);

}
TouchSilder.prototype._onTouchStart = function () {
    this.SetAutoScrollFunc(null);
    if (this._autoTimer != null) {
        clearInterval(this._autoTimer);
        this._autoTimer = null;
    }

    this._tcPT.X = event.targetTouches[0].pageX;
    this._tcPT.Y = event.targetTouches[0].pageY;
    this._tcPT.cntX = this.cnt.scrollLeft;
    this._tcPT.cntY = this.cnt.scrollTop;
    this._tcPT.stepX = 0;
    this._tcPT.stepY = 0;
    this._tcPT.iCancel = 0;
}

TouchSilder.prototype.onSilded = function (index) { };
TouchSilder.prototype.start = function () {
    var me = this;
    this.cnt.addEventListener("touchstart", function () { me._onTouchStart(); });
    this.cnt.addEventListener("touchmove", function () { me._onTouchMove(); });
    this.cnt.addEventListener("touchend", function () { me._onTouchEnd(); });
    if (this._tcPT.delay >= 1000)
        this._autoTimer = setInterval(function () { me.AutoSilder(); }, this._tcPT.delay);
}
TouchSilder.prototype._fpTimer = null;
TouchSilder.prototype.ScrollTo = function (endPx) {
    var cX = this.cnt.scrollLeft;
    var dx = endPx - cX;
    dx = Math.round(dx / 4);

    if (Math.abs(dx) < 10)
        dx = Math.abs(dx) / dx * 10;
    if (Math.abs(endPx - cX) < 10) {
        dx = endPx - cX;
        this.SetAutoScrollFunc(null);
        if (this.onSilded != null) {
            var i = Math.round(this.cnt.scrollLeft / this.cnt.clientWidth);
            try { this.onSilded(i) } catch (e) { alert(e.message) }
        }
    }
    this.cnt.scrollLeft += dx;
}
TouchSilder.prototype._autoTimer = null;
TouchSilder.prototype.AutoSilder = function () {
    var me = this;
    var endPx;
    var cW = this.cnt.clientWidth;
    var maxCount = parseInt(this.cnt.scrollWidth / cW);
    var cIdx = parseInt(this.cnt.scrollLeft / this.cnt.clientWidth);
    endPx = (cIdx + 1) * cW;
    if (endPx + cW > this.cnt.scrollWidth)
        endPx = 0;
    this.SetAutoScrollFunc(function () { me.ScrollTo(endPx); });
}
TouchSilder.prototype.stop = function () {
    if (this._autoTimer != null)
        clearInterval(this._autoTimer);
    this.SetAutoScrollFunc(null);
    this._autoTimer = null;
}
TouchSilder.prototype.SetAutoScrollFunc = function (func) {
    if (this._fpTimer != null)
        clearInterval(this._fpTimer);
    this._fpTimer = null;
    if (func == null)
        return;
    this._fpTimer = setInterval(func, 20);
}