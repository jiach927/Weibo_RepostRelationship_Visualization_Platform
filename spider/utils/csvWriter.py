import csv
# import pandas as pd


class csvWriter(object):

    def __init__(self, filename, search=False, repost=False, temp=False):
        self.filename = filename
        self.search = search
        self.repost = repost
        self.temp = temp
        if self.search:
            self.header = ['keyword', 'user_id', 'screen_name', 'bw_id', 'repost_count', 'topic', 'content', 'created_at']
            self.create_csv()
        if self.repost:
            self.header = ['center_bw_id', 'user_id', 'screen_name', 'bw_id', 'origin', 'repost_count', 'fs_count', 'fs_user_id', 'fs_screen_name', 'fs_bw_id', 'fs_fans_count', 'level', 'raw_text', 'created_at']
            self.create_csv()
        if self.temp:
            self.header = ['bw_id']
            self.create_csv()

    # 创建初始空文件
    def create_csv(self):
        with open(self.filename, 'w', encoding='utf-8', newline='') as f:
            csv_writer = csv.DictWriter(f, self.header)
            csv_writer.writeheader()

    # 写入每个页面的字典列表
    # 当爬取到最后一层时，才会用到关键字参数
    def write_csv(self, result_list, END=False, center_bw_id=None, origin_info=None, level=None):
        # 打开待写入文件
        with open(self.filename, 'a', encoding='utf-8', newline='') as f:
            csv_writer = csv.DictWriter(f, self.header)
            # 判断是否最后一层
            if END:
                # 若已是最后一层转发，许多字段需要设为空
                this_dict = {
                    'center_bw_id': center_bw_id,
                    'user_id': origin_info['origin_user']['id'],
                    'screen_name': origin_info['origin_user']['screen_name'],
                    'bw_id': origin_info['bw_id'],
                    'origin': origin_info['origin'],
                    'repost_count': 0,
                    'fs_count': origin_info['origin_user']['followers_count'],
                    'fs_user_id': 'Null',
                    'fs_screen_name': 'Null',
                    'fs_bw_id': 'Null',
                    'fs_fans_count': 'Null',
                    'level': level,
                    'raw_text': 'Null',
                    'created_at': 'Null'
                }
                csv_writer.writerow(this_dict)
            else:
                csv_writer.writerows(result_list)

    # 去重
    # pandas对大文件去重慢，交由数据库端进行去重
    '''
    def drop_duplicate(self):
        # 默认第一行作为表头
        # 为了确保数据库导入
        df = pd.read_csv(self.filename, encoding='utf-8')
        df.drop_duplicates(keep='last', inplace=True)
        # 去除Pandas给得行索引index
        df.to_csv(self.filename, index=False, encoding='utf-8', sep=',')
    '''

    # 获取要爬取转发关系的列表
    def get_idList(self):
        with open(self.filename, 'r', encoding='utf-8') as f:
            reader = csv.DictReader(f)
            if self.search or self.temp:
                idList = [row['bw_id'] for row in reader]
            return idList