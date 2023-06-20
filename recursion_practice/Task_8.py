import os


def get_all_files(path: str) -> list[str]:
    files = []
    for i in os.listdir(path):
        if os.path.isfile(f'{path}/{i}'):
            files.append(i)
        if os.path.isdir(f'{path}/{i}'):
            files += get_all_files(f'{path}/{i}')
    return files
