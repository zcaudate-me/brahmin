var b = brahmin.core;
var c = cljs.core;

describe("Testing fmap", function() {
  it("should preserve container type", function() {
    expect(b.fmap(c.inc, [1,2,3,4])).toEqual([2,3,4,5]);
  });
});