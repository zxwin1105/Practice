Java中容器主要包括Collection和Map两种，Collection存储对象集合；Map存储键值对。

## 一、Java集合分类

### 1.1 Set集合

- TreeSet: 基于红黑树实现，支持有序性操作（根据一个范围查找元素），查找效率为O(logN)。

- HashSet: 基于哈希表实现，支持快速查找，但不支持有序性操作，且无序（失去了元素插入时的顺序信息）。

- LinkedHashSet: 具有HashSet的查找效率，且内部使用双向链表维护元素插入顺序。

### 1.2 List集合

- ArrayList: 基于动态数组实现，支持随机访问，从中间插入（删除）元素时复杂度较高。

- Vector: 和ArrayList基本类似，但是支持线程安全操作。

- LinkedList: 基于双向链表实现，只能顺序访问，从中间插入（删除）元素时复杂度较低。LinkedList也可以方便的作为
栈、队列、双向队列使用。
  
### 1.3 Queue

- LinkedList: 实现双向队列

- PriorityQueue: 基于堆结构实现，可以用太
  http://tlmdatabank.tlm.pcep.cloud/api/dde/masterdataservice/CD_STATION?identity=F9E3BD9113764C5D80FDEB20250682FF6DFE78BFE3D3F1CCMMZWILRQGRRDEKZTMNQTEKZSGJQDKKZWMEXGGKZPGRQDMYRVF4ZDINRSF4XT2ZTXNJYG2LTBNRRXC53FGRXGYLZSGRYDGNLUN4XTALZVGEYDGMZOGE3TOLRPGAYDELZQGAZA&page=0&size=200&byuser=false
  Bearer eyJhbGciOiJSUzI1NiJ9.eyJsb2dpbl9uYW1lIjoiVDA2MTg0MTAiLCJ1c2VyX2lkIjoiaHNheXFreTFldHNsY2l6ZXZja3lpdnpkIiwib3JnYW5pemF0aW9uIjoiT1JHQVRMMTEwMDA1MTg2IiwiaXNzIjoiYTM2YzMwNDliMzYyNDlhM2M5Zjg4OTFjYjEyNzI0M2MiLCJkaXNwbGF5X25hbWUiOiLltJTlvIDpmJQiLCJyZWdpb24iOiJUTCIsImlhdCI6MTY3MTQzNTkyOSwiZXhwIjoxNjc0MDI3OTI5fQ.QkvsF7MutUg3h85dYLqwig-k-PnVf6O_oy3trKOBXFRIpsEAeFKQ1aK5N1BEMKZ7pzO4D7g8EqvH3yfd8mbRnlAB_EeiJ6_Pv3QyNk0J9TF8dlRlWy8HJUnFfo0ccrrnXI2Q9cOwlK_CYktXHhJlOCyavtA6pE7ljzxxwlH5bfvS3oAAZnlrqE61djKgv9Uddv_wDcqCb-Vv7m1VCz3pkPLVKlMfQfVSh9UysRt8KpJUBu2wgylQCyWTXWlGW6mrrhr0HrgTB_F22pK2VlijXiXdxV0RfBC8gqEESDeOv9j5EZgRDVv-l6cNrDGJgUiRUaae0u5J2s-YAf3Qu3LNYQ