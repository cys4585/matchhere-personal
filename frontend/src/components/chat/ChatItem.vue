<template>
  <div class="flex flex-col gap-1 w-full">
    <div
      v-if="!isContinue"
      class="mt-4 flex gap-2"
      :class="{ 'flex-row-reverse': isMe }"
    >
      <img
        :src="chatItem.pic_uri || require('@/assets/images/test-profile.png')"
        alt="사진"
        class="rounded-full w-10 h-10"
      />
      <div class="flex flex-col" :class="{ 'items-end': isMe }">
        <p class="font-medium text-sm text-gray-900">
          {{ chatItem.nickname }}
        </p>
        <p class="text-gray-500 text-sm">어제, 오후 06:01</p>
      </div>
    </div>
    <div
      class="border-2 rounded-xl py-1 px-2 mx-12 whitespace-pre-line break-all"
      style="max-width: 250px"
      :class="[
        isMe
          ? 'border-blue-500 rounded-tr-none self-end'
          : 'border-orange-400 rounded-tl-none self-start',
      ]"
    >
      <span
        class="text-sm"
        style="font-family: 'Noto Sans KR'"
        v-html="chatItem.content.replace(/(?:\r\n|\r|\n)/g, '<br />')"
      ></span>
    </div>
  </div>
</template>

<script>
export default {
  name: "ChatItem",
  props: ["exChatItemId", "chatItem", "myId"],
  setup(props) {
    // console.log(props)
    const isContinue =
      String(props.exChatItemId) &&
      String(props.chatItem.sender_id) === String(props.exChatItemId)
    const isMe = String(props.myId) === String(props.chatItem.sender_id)

    return { isContinue, isMe }
  },
}
</script>

<style lang="scss" scoped></style>
