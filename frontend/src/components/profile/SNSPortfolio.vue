<template>
  <div class="grid gap-10">
    <section>
      <header>
        <h3>포트폴리오</h3>
        <button>
          <span
            class="material-icons-outlined text-blue-400 hover:text-blue-500"
            >settings</span
          >
        </button>
      </header>
      <div class="grid gap-4">
        <div class="file flex">
          <p class="flex-1">{{ portfolio.file_name }}</p>
          <span
            class="material-icons-outlined text-gray-700"
            style="font-size: 1.25rem"
          >
            file_download
          </span>
        </div>
        <div class="link flex">
          <p class="flex-1">{{ portfolioUri }}</p>
          <span
            class="material-icons-outlined text-gray-700"
            style="font-size: 1.25rem"
          >
            open_in_new
          </span>
        </div>
      </div>
    </section>
    <section>
      <header>
        <h3>SNS 링크</h3>
        <button>
          <span
            class="material-icons-outlined text-blue-400 hover:text-blue-500"
          >
            settings
          </span>
        </button>
      </header>
      <div class="grid gap-4">
        <div
          class="link flex items-center"
          v-for="sns in snsList"
          :key="sns.id"
        >
          <div class="flex-1 grid gap-2">
            <p>{{ sns.snsAccount }}</p>
            <p class="text-sm text-gray-700">{{ sns.snsName }}</p>
          </div>
          <span
            class="material-icons-outlined text-gray-700"
            style="font-size: 1.25rem"
          >
            open_in_new
          </span>
        </div>
      </div>
    </section>
    <PortfolioFormModal />
  </div>
</template>

<script>
import { onMounted, reactive, ref } from "@vue/runtime-core"
import PortfolioFormModal from "@/components/profile/PortfolioFormModal.vue"
const DATA = {
  portfolio: {
    download_uri: "https://www.instagram.com/kim.gam.ja/",
    file_name: "FE_김병훈_이력서.pdf",
    file_type: "string",
    id: "string",
  },
  portfolio_uri: "https://www.instagram.com/kim.gam.ja/",
  snsList: [
    {
      id: 1,
      snsName: "github",
      snsAccount: "https://www.instagram.com/kim.gam.ja/",
    },
    {
      id: 2,
      snsName: "twitter",
      snsAccount: "https://www.instagram.com/kim.gam.ja/",
    },
  ],
}

export default {
  name: "SNSPortfolio",
  components: { PortfolioFormModal },
  setup() {
    const portfolio = reactive({
      id: "",
      file_name: "",
      file_type: "",
      download_uri: "",
    })
    const portfolioUri = ref("")
    const snsList = ref([])

    onMounted(() => {
      Object.keys(DATA.portfolio).forEach((key) => {
        portfolio[key] = DATA.portfolio[key]
      })
      portfolioUri.value = DATA.portfolio_uri
      snsList.value = [...DATA.snsList]
    })
    return {
      portfolio,
      portfolioUri,
      snsList,
    }
  },
}
</script>

<style lang="scss" scoped>
section {
  @apply grid gap-4;

  header {
    @apply flex items-center justify-between;

    .add-button {
      @apply text-blue-600 font-medium py-1 px-2 rounded transition-colors hover:text-white hover:bg-blue-600;
    }
  }
}
</style>
